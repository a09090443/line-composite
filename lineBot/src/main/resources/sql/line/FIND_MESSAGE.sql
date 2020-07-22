SELECT
	ms.*, messageDetails.*
FROM
	message_setting ms
INNER JOIN message_mapping mm ON
	ms.id = mm.message_id
INNER JOIN message_detail messageDetails ON
	mm.detail_id = messageDetails.detail_id
WHERE
	ms.`key` = '抽'
