SELECT
    DISTINCT op2.Id AS id,
             op2.Name AS name,
             op2.Content AS content,
             op2.`Type` AS `type`,
             op2.Enabled AS enabled,
             op2.`Sequence` AS `sequence`,
             op2.ParentId AS parentId,
             op2.ChannelId AS channelId
FROM
    OrderProcess op
        INNER JOIN OrderProcess op2 ON
                op.Id = op2.ParentId
            OR op.Id = op2.Id
WHERE
        op.Name = :name
  AND op.ChannelId = :channelId
ORDER BY
    op2.`Sequence` ;
