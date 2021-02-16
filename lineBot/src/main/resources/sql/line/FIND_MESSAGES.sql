SELECT
    ms.Name AS name,
    md.Value AS value,
	md.`Type` AS `type`,
	md.ChannelId AS channelId
FROM
    MessageSetting ms
    INNER JOIN MessageMapping mm ON
    ms.Id = mm.MessageId
    INNER JOIN MessageDetail md ON
    mm.DetailId = md.Id
WHERE
    ms.Name = :name
