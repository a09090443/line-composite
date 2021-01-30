package com.zipe.annotation

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DBRouting(val datasourceName: String){
    companion object {
        const val PRIMARY_DATASOURCE = "primaryDataSource"
        const val SECONDARY_DATASOURCE = "secondaryDataSource"
    }
}
