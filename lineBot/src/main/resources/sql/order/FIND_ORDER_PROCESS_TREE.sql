SELECT
    DISTINCT op2.*
FROM
    order_process op
        INNER JOIN order_process op2 ON
                op.process_id = op2.parent_id
            OR op.process_id = op2.process_id
WHERE
        op.process_name = :name AND op.line_id = :channelId
ORDER BY
    op2.`sequence`
