SELECT
    li.LineId AS lineId,
    li.Name AS name
FROM
    LineInfo li
        INNER JOIN LineMapping lm ON
            li.LineId = lm.InfoId
        INNER JOIN LineChannel lc ON
            lm.ChannelId = lc.ChannelId
WHERE
  lc.ChannelId = :channelId
  AND li.Name IN (:name)
  AND li.`Type` IN (:types)
