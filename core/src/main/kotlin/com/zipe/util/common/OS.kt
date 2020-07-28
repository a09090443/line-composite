package com.zipe.util.common

import java.io.*
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.LinkedHashSet

object OS {
    private object osDetector : Detector() {
        val detectedProperties = System.getProperties()

        init {
            detect(detectedProperties, emptyList())
        }

        val os: String = detectedProperties[DETECTED_NAME] as String
        val arch: String = detectedProperties[DETECTED_ARCH] as String
        val classifier: String = detectedProperties[DETECTED_CLASSIFIER] as String
    }

    val os: String = osDetector.os
    val arch: String = osDetector.arch
    val classifier: String = osDetector.classifier

    const val windows: String = "windows"
    const val linux: String = "linux"
    const val osx: String = "osx"

    const val x86_32: String = "x86_32"
    const val x86_64: String = "x86_64"

    val isWindows: Boolean = os == windows
    val isLinux: Boolean = os == linux
    val isOSX: Boolean = os == osx

    val is32bit: Boolean = arch == x86_32
    val is64bit: Boolean = arch == x86_64
}

abstract class Detector {
    protected fun detect(props: Properties, classifierWithLikes: List<String?>) {
        val allProps = Properties(System.getProperties())
        allProps.putAll(props)
        val osName = allProps.getProperty("os.name")
        val osArch = allProps.getProperty("os.arch")
        val osVersion = allProps.getProperty("os.version")
        val detectedName = normalizeOs(osName)
        val detectedArch = normalizeArch(osArch)
        setProperty(props, DETECTED_NAME, detectedName)
        setProperty(props, DETECTED_ARCH, detectedArch)
        val versionMatcher = VERSION_REGEX.matcher(osVersion)
        if (versionMatcher.matches()) {
            setProperty(props, DETECTED_VERSION, versionMatcher.group(1))
            setProperty(props, DETECTED_VERSION_MAJOR, versionMatcher.group(2))
            setProperty(props, DETECTED_VERSION_MINOR, versionMatcher.group(3))
        }
        val failOnUnknownOS = allProps.getProperty("failOnUnknownOS")
        if (!"false".equals(failOnUnknownOS, ignoreCase = true)) {
            if (UNKNOWN == detectedName)
                error("unknown os.name: $osName")
            if (UNKNOWN == detectedArch)
                error("unknown os.arch: $osArch")
        }

        // Assume the default classifier, without any os "like" extension.
        val detectedClassifierBuilder = StringBuilder()
        detectedClassifierBuilder.append(detectedName)
        detectedClassifierBuilder.append('-')
        detectedClassifierBuilder.append(detectedArch)

        // For Linux systems, add additional properties regarding details of the OS.
        val linuxRelease = if ("linux" == detectedName) linuxRelease else null
        if (linuxRelease != null) {
            setProperty(props, DETECTED_RELEASE, linuxRelease.id)
            if (linuxRelease.version != null) {
                setProperty(props, DETECTED_RELEASE_VERSION, linuxRelease.version)
            }

            // Add properties for all systems that this OS is "like".
            for (like in linuxRelease.like) {
                val propKey = DETECTED_RELEASE_LIKE_PREFIX + like
                setProperty(props, propKey, "true")
            }

            // If any of the requested classifier likes are found in the "likes" for this system,
            // append it to the classifier.
            for (classifierLike in classifierWithLikes) {
                if (linuxRelease.like.contains(classifierLike)) {
                    detectedClassifierBuilder.append('-')
                    detectedClassifierBuilder.append(classifierLike)
                    // First one wins.
                    break
                }
            }
        }
        setProperty(props, DETECTED_CLASSIFIER, detectedClassifierBuilder.toString())
    }

    private fun setProperty(props: Properties, name: String, value: String?) {
        props.setProperty(name, value)
        System.setProperty(name, value)
    }

    private class LinuxRelease internal constructor(
        val id: String,
        val version: String?,
        like: Set<String?>?
    ) {
        val like: Collection<String?> = Collections.unmodifiableCollection(like)
    }

    companion object {
        const val DETECTED_NAME = "os.detected.name"
        const val DETECTED_ARCH = "os.detected.arch"
        const val DETECTED_VERSION = "os.detected.version"
        const val DETECTED_VERSION_MAJOR = "$DETECTED_VERSION.major"
        const val DETECTED_VERSION_MINOR = "$DETECTED_VERSION.minor"
        const val DETECTED_CLASSIFIER = "os.detected.classifier"
        const val DETECTED_RELEASE = "os.detected.release"
        const val DETECTED_RELEASE_VERSION = "$DETECTED_RELEASE.version"
        const val DETECTED_RELEASE_LIKE_PREFIX = "$DETECTED_RELEASE.like."
        private const val UNKNOWN = "unknown"
        private const val LINUX_ID_PREFIX = "ID="
        private const val LINUX_ID_LIKE_PREFIX = "ID_LIKE="
        private const val LINUX_VERSION_ID_PREFIX = "VERSION_ID="
        private val LINUX_OS_RELEASE_FILES =
            arrayOf("/etc/os-release", "/usr/lib/os-release")
        private const val REDHAT_RELEASE_FILE = "/etc/redhat-release"
        private val DEFAULT_REDHAT_VARIANTS = arrayOf("rhel", "fedora")
        private val VERSION_REGEX = Pattern.compile("((\\d+)\\.(\\d+)).*")
        private val REDHAT_MAJOR_VERSION_REGEX = Pattern.compile("(\\d+)")
        private fun normalizeOs(value: String): String {
            var value = value
            value = normalize(value)
            if (value.startsWith("aix")) {
                return "aix"
            }
            if (value.startsWith("hpux")) {
                return "hpux"
            }
            if (value.startsWith("os400")) {
                // Avoid the names such as os4000
                if (value.length <= 5 || !Character.isDigit(value[5])) {
                    return "os400"
                }
            }
            if (value.startsWith("linux")) {
                return "linux"
            }
            if (value.startsWith("macosx") || value.startsWith("osx")) {
                return "osx"
            }
            if (value.startsWith("freebsd")) {
                return "freebsd"
            }
            if (value.startsWith("openbsd")) {
                return "openbsd"
            }
            if (value.startsWith("netbsd")) {
                return "netbsd"
            }
            if (value.startsWith("solaris") || value.startsWith("sunos")) {
                return "sunos"
            }
            if (value.startsWith("windows")) {
                return "windows"
            }
            return if (value.startsWith("zos")) {
                "zos"
            } else UNKNOWN
        }

        private fun normalizeArch(value: String): String {
            var value = value
            value = normalize(value)
            if (value.matches(Regex("^(x8664|amd64|ia32e|em64t|x64)$"))) {
                return "x86_64"
            }
            if (value.matches(Regex("^(x8632|x86|i[3-6]86|ia32|x32)$"))) {
                return "x86_32"
            }
            if (value.matches(Regex("^(ia64w?|itanium64)$"))) {
                return "itanium_64"
            }
            if ("ia64n" == value) {
                return "itanium_32"
            }
            if (value.matches(Regex("^(sparc|sparc32)$"))) {
                return "sparc_32"
            }
            if (value.matches(Regex("^(sparcv9|sparc64)$"))) {
                return "sparc_64"
            }
            if (value.matches(Regex("^(arm|arm32)$"))) {
                return "arm_32"
            }
            if ("aarch64" == value) {
                return "aarch_64"
            }
            if (value.matches(Regex("^(mips|mips32)$"))) {
                return "mips_32"
            }
            if (value.matches(Regex("^(mipsel|mips32el)$"))) {
                return "mipsel_32"
            }
            if ("mips64" == value) {
                return "mips_64"
            }
            if ("mips64el" == value) {
                return "mipsel_64"
            }
            if (value.matches(Regex("^(ppc|ppc32)$"))) {
                return "ppc_32"
            }
            if (value.matches(Regex("^(ppcle|ppc32le)$"))) {
                return "ppcle_32"
            }
            if ("ppc64" == value) {
                return "ppc_64"
            }
            if ("ppc64le" == value) {
                return "ppcle_64"
            }
            if ("s390" == value) {
                return "s390_32"
            }
            return if ("s390x" == value) {
                "s390_64"
            } else UNKNOWN
        }

        private fun normalize(value: String?): String {
            return value?.toLowerCase(Locale.US)?.replace("[^a-z0-9]+".toRegex(), "") ?: ""
        }

        // First, look for the os-release file.

        // Older versions of redhat don't have /etc/os-release. In this case, try
        // parsing this file.
        private val linuxRelease: LinuxRelease?
            get() {
                // First, look for the os-release file.
                for (osReleaseFileName in LINUX_OS_RELEASE_FILES) {
                    val file = File(osReleaseFileName)
                    if (file.exists()) {
                        return parseLinuxOsReleaseFile(file)
                    }
                }

                // Older versions of redhat don't have /etc/os-release. In this case, try
                // parsing this file.
                val file = File(REDHAT_RELEASE_FILE)
                return if (file.exists()) {
                    parseLinuxRedhatReleaseFile(file)
                } else null
            }

        /**
         * Parses a file in the format of `/etc/os-release` and return a [LinuxRelease]
         * based on the `ID`, `ID_LIKE`, and `VERSION_ID` entries.
         */
        private fun parseLinuxOsReleaseFile(file: File): LinuxRelease? {
            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(InputStreamReader(FileInputStream(file), "utf-8"))
                var id: String? = null
                var version: String? = null
                val likeSet: MutableSet<String?> = LinkedHashSet()
                var line: String
                while (reader.readLine().also { line = it } != null) {
                    // Parse the ID line.
                    if (line.startsWith(LINUX_ID_PREFIX)) {
                        // Set the ID for this version.
                        id = normalizeOsReleaseValue(line.substring(LINUX_ID_PREFIX.length))

                        // Also add the ID to the "like" set.
                        likeSet.add(id)
                        continue
                    }

                    // Parse the VERSION_ID line.
                    if (line.startsWith(LINUX_VERSION_ID_PREFIX)) {
                        // Set the ID for this version.
                        version =
                            normalizeOsReleaseValue(line.substring(LINUX_VERSION_ID_PREFIX.length))
                        continue
                    }

                    // Parse the ID_LIKE line.
                    if (line.startsWith(LINUX_ID_LIKE_PREFIX)) {
                        line =
                            normalizeOsReleaseValue(line.substring(LINUX_ID_LIKE_PREFIX.length))

                        // Split the line on any whitespace.
                        val parts = line.split("\\s+").toTypedArray()
                        Collections.addAll(likeSet, *parts)
                    }
                }
                if (id != null) {
                    return LinuxRelease(id, version, likeSet)
                }
            } catch (ignored: IOException) {
                // Just absorb. Don't treat failure to read /etc/os-release as an error.
            } finally {
                closeQuietly(reader)
            }
            return null
        }

        /**
         * Parses the `/etc/redhat-release` and returns a [LinuxRelease] containing the
         * ID and like ["rhel", "fedora", ID]. Currently only supported for CentOS, Fedora, and RHEL.
         * Other variants will return `null`.
         */
        private fun parseLinuxRedhatReleaseFile(file: File): LinuxRelease? {
            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(InputStreamReader(FileInputStream(file), "utf-8"))

                // There is only a single line in this file.
                var line = reader.readLine()
                if (line != null) {
                    line = line.toLowerCase(Locale.US)
                    val id: String
                    var version: String? = null
                    id = if (line.contains("centos")) {
                        "centos"
                    } else if (line.contains("fedora")) {
                        "fedora"
                    } else if (line.contains("red hat enterprise linux")) {
                        "rhel"
                    } else {
                        // Other variants are not currently supported.
                        return null
                    }
                    val versionMatcher = REDHAT_MAJOR_VERSION_REGEX.matcher(line)
                    if (versionMatcher.find()) {
                        version = versionMatcher.group(1)
                    }
                    val likeSet: MutableSet<String?> =
                        LinkedHashSet(Arrays.asList(*DEFAULT_REDHAT_VARIANTS))
                    likeSet.add(id)
                    return LinuxRelease(id, version, likeSet)
                }
            } catch (ignored: IOException) {
                // Just absorb. Don't treat failure to read /etc/os-release as an error.
            } finally {
                closeQuietly(reader)
            }
            return null
        }

        private fun normalizeOsReleaseValue(value: String): String {
            // Remove any quotes from the string.
            return value.trim { it <= ' ' }.replace("\"", "")
        }

        private fun closeQuietly(obj: Closeable?) {
            try {
                obj?.close()
            } catch (ignored: IOException) {
                // Ignore.
            }
        }
    }
}
