-- line.QRTZ_CALENDARS definition

CREATE TABLE `QRTZ_CALENDARS`
(
    `SCHED_NAME`    varchar(120) NOT NULL,
    `CALENDAR_NAME` varchar(190) NOT NULL,
    `CALENDAR`      blob         NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.QRTZ_FIRED_TRIGGERS definition

CREATE TABLE `QRTZ_FIRED_TRIGGERS`
(
    `SCHED_NAME`        varchar(120) NOT NULL,
    `ENTRY_ID`          varchar(95)  NOT NULL,
    `TRIGGER_NAME`      varchar(190) NOT NULL,
    `TRIGGER_GROUP`     varchar(190) NOT NULL,
    `INSTANCE_NAME`     varchar(190) NOT NULL,
    `FIRED_TIME`        bigint(13) NOT NULL,
    `SCHED_TIME`        bigint(13) NOT NULL,
    `PRIORITY`          int(11) NOT NULL,
    `STATE`             varchar(16)  NOT NULL,
    `JOB_NAME`          varchar(190) DEFAULT NULL,
    `JOB_GROUP`         varchar(190) DEFAULT NULL,
    `IS_NONCONCURRENT`  varchar(1)   DEFAULT NULL,
    `REQUESTS_RECOVERY` varchar(1)   DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`),
    KEY                 `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
    KEY                 `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
    KEY                 `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
    KEY                 `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
    KEY                 `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    KEY                 `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.QRTZ_JOB_DETAILS definition

CREATE TABLE `QRTZ_JOB_DETAILS`
(
    `SCHED_NAME`        varchar(120) NOT NULL,
    `JOB_NAME`          varchar(190) NOT NULL,
    `JOB_GROUP`         varchar(190) NOT NULL,
    `DESCRIPTION`       varchar(250) DEFAULT NULL,
    `JOB_CLASS_NAME`    varchar(250) NOT NULL,
    `IS_DURABLE`        varchar(1)   NOT NULL,
    `IS_NONCONCURRENT`  varchar(1)   NOT NULL,
    `IS_UPDATE_DATA`    varchar(1)   NOT NULL,
    `REQUESTS_RECOVERY` varchar(1)   NOT NULL,
    `JOB_DATA`          blob         DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`),
    KEY                 `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
    KEY                 `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.QRTZ_LOCKS definition

CREATE TABLE `QRTZ_LOCKS`
(
    `SCHED_NAME` varchar(120) NOT NULL,
    `LOCK_NAME`  varchar(40)  NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.QRTZ_PAUSED_TRIGGER_GRPS definition

CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`
(
    `SCHED_NAME`    varchar(120) NOT NULL,
    `TRIGGER_GROUP` varchar(190) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.QRTZ_SCHEDULER_STATE definition

CREATE TABLE `QRTZ_SCHEDULER_STATE`
(
    `SCHED_NAME`        varchar(120) NOT NULL,
    `INSTANCE_NAME`     varchar(190) NOT NULL,
    `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
    `CHECKIN_INTERVAL`  bigint(13) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.QRTZ_TRIGGERS definition

CREATE TABLE `QRTZ_TRIGGERS`
(
    `SCHED_NAME`     varchar(120) NOT NULL,
    `TRIGGER_NAME`   varchar(190) NOT NULL,
    `TRIGGER_GROUP`  varchar(190) NOT NULL,
    `JOB_NAME`       varchar(190) NOT NULL,
    `JOB_GROUP`      varchar(190) NOT NULL,
    `DESCRIPTION`    varchar(250) DEFAULT NULL,
    `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
    `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
    `PRIORITY`       int(11) DEFAULT NULL,
    `TRIGGER_STATE`  varchar(16)  NOT NULL,
    `TRIGGER_TYPE`   varchar(8)   NOT NULL,
    `START_TIME`     bigint(13) NOT NULL,
    `END_TIME`       bigint(13) DEFAULT NULL,
    `CALENDAR_NAME`  varchar(190) DEFAULT NULL,
    `MISFIRE_INSTR`  smallint(2) DEFAULT NULL,
    `JOB_DATA`       blob         DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
    KEY              `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
    KEY              `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
    KEY              `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
    KEY              `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
    KEY              `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
    KEY              `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
    KEY              `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
    KEY              `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
    KEY              `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
    KEY              `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
    KEY              `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
    KEY              `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
    CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.QRTZ_BLOB_TRIGGERS definition

CREATE TABLE `QRTZ_BLOB_TRIGGERS`
(
    `SCHED_NAME`    varchar(120) NOT NULL,
    `TRIGGER_NAME`  varchar(190) NOT NULL,
    `TRIGGER_GROUP` varchar(190) NOT NULL,
    `BLOB_DATA`     blob DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
    KEY             `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.QRTZ_CRON_TRIGGERS definition

CREATE TABLE `QRTZ_CRON_TRIGGERS`
(
    `SCHED_NAME`      varchar(120) NOT NULL,
    `TRIGGER_NAME`    varchar(190) NOT NULL,
    `TRIGGER_GROUP`   varchar(190) NOT NULL,
    `CRON_EXPRESSION` varchar(120) NOT NULL,
    `TIME_ZONE_ID`    varchar(80) DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.QRTZ_SIMPLE_TRIGGERS definition

CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`
(
    `SCHED_NAME`      varchar(120) NOT NULL,
    `TRIGGER_NAME`    varchar(190) NOT NULL,
    `TRIGGER_GROUP`   varchar(190) NOT NULL,
    `REPEAT_COUNT`    bigint(7) NOT NULL,
    `REPEAT_INTERVAL` bigint(12) NOT NULL,
    `TIMES_TRIGGERED` bigint(10) NOT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.QRTZ_SIMPROP_TRIGGERS definition

CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`
(
    `SCHED_NAME`    varchar(120) NOT NULL,
    `TRIGGER_NAME`  varchar(190) NOT NULL,
    `TRIGGER_GROUP` varchar(190) NOT NULL,
    `STR_PROP_1`    varchar(512)   DEFAULT NULL,
    `STR_PROP_2`    varchar(512)   DEFAULT NULL,
    `STR_PROP_3`    varchar(512)   DEFAULT NULL,
    `INT_PROP_1`    int(11) DEFAULT NULL,
    `INT_PROP_2`    int(11) DEFAULT NULL,
    `LONG_PROP_1`   bigint(20) DEFAULT NULL,
    `LONG_PROP_2`   bigint(20) DEFAULT NULL,
    `DEC_PROP_1`    decimal(13, 4) DEFAULT NULL,
    `DEC_PROP_2`    decimal(13, 4) DEFAULT NULL,
    `BOOL_PROP_1`   varchar(1)     DEFAULT NULL,
    `BOOL_PROP_2`   varchar(1)     DEFAULT NULL,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
    CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- line.LineChannel definition

CREATE TABLE `LineChannel`
(
    `ChannelId`     varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '頻道 Id',
    `ChannelSecret` varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '頻道 Secret key',
    `BotId`         varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Bot Id',
    `Name`          varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '頻道名稱',
    `Description`   varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '頻道描述',
    `Email`         varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '頻道 Email',
    `UserId`        varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '頻道使用者 Id',
    `AccessToken`   varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '頻道 Access Token',
    `LineStoreId`   varchar(10) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '對應 LineStore ChannelId',
    `CreateTime`    datetime                             DEFAULT NULL COMMENT '建立時間',
    `Creator`       varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '建立者',
    `UpdateTime`    datetime                             DEFAULT NULL COMMENT '更新時間',
    `Updater`       varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`ChannelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Line 頻道';

-- line.LineInfo definition

CREATE TABLE `LineInfo`
(
    `LineId`        varchar(33) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Lind Id',
    `Name`          varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '名稱',
    `StatusMessage` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '訊息狀態',
    `Type`          varchar(10) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '型態',
    `Status`        varchar(10) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '狀態',
    `CreateTime`    datetime                             DEFAULT NULL COMMENT '建立時間',
    `Creator`       varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '建立者',
    `UpdateTime`    datetime                             DEFAULT NULL COMMENT '更新時間',
    `Updater`       varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`LineId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Line 使用者';

-- line.LineMapping definition

CREATE TABLE `LineMapping`
(
    `ChannelId` char(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '頻道 Id',
    `InfoId`    char(33) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Line 使用者 Id',
    KEY         `FKioaye930tu3ntb5gtcynax145` (`InfoId`),
    KEY         `FK583ocypqd98o397viimidrfwb` (`ChannelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Line 頻道對應 Line 使用者';

-- line.LineStore definition

CREATE TABLE `LineStore`
(
    `ChannelId`     varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Line 頻道 Id',
    `ChannelSecret` varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT 'Line 頻道 Secret',
    `Name`          varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商店名稱',
    `Description`   varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '描述',
    `Email`         varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '聯絡人 Email',
    `CreateTime`    datetime                             DEFAULT NULL COMMENT '建立時間',
    `Creator`       varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '建立者',
    `UpdateTime`    datetime                             DEFAULT NULL COMMENT '更新時間',
    `Updater`       varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`ChannelId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Line 商店';

-- line.MessageDetail definition

CREATE TABLE `MessageDetail`
(
    `Id`         char(32) COLLATE utf8_unicode_ci    NOT NULL COMMENT '訊息 Id',
    `Value`      longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '訊息內容',
    `Type`       varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '訊息類型',
    `ChannelId`  varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '頻道 Id',
    `CreateTime` datetime                            DEFAULT NULL COMMENT '建立時間',
    `Creator`    varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '建立者',
    `UpdateTime` datetime                            DEFAULT NULL COMMENT '更新時間',
    `Updater`    varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='訊息庫';

-- line.MessageMapping definition

CREATE TABLE `MessageMapping`
(
    `MessageId` char(32) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Message Id',
    `DetailId`  char(32) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Detail Id',
    KEY         `FK2wuamnypiorhuvbnqgcm5l5mn` (`DetailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Message id 對應 Message value';

-- line.MessageSetting definition

CREATE TABLE `MessageSetting`
(
    `Id`         char(32) COLLATE utf8_unicode_ci    NOT NULL COMMENT '流水號',
    `Name`       varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '關鍵字名稱',
    `Comment`    varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '說明',
    `CreateTime` datetime                             DEFAULT NULL COMMENT '建立時間',
    `Creator`    varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '建立者',
    `UpdateTime` datetime                             DEFAULT NULL COMMENT '更新時間',
    `Updater`    varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='訊息關鍵字設定';

-- line.OrderProcess definition

CREATE TABLE `OrderProcess`
(
    `Id`         int(5) NOT NULL COMMENT '主流程Id',
    `Name`       varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '流程名稱',
    `Content`    longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Line 回應內容',
    `Type`       varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Line 訊息型態',
    `Enabled`    char(1) COLLATE utf8_unicode_ci     DEFAULT NULL COMMENT '流程開關',
    `Sequence`   int(5) NOT NULL COMMENT '流程順序',
    `ParentId`   int(5) NOT NULL COMMENT '對應主流程Id',
    `ChannelId`  varchar(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'Line 頻道 Id',
    `CreateTime` datetime                            DEFAULT NULL COMMENT '建立時間',
    `Creator`    varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '建立者',
    `UpdateTime` datetime                            DEFAULT NULL COMMENT '更新時間',
    `Updater`    varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='購買流程';
-- line.Product definition

CREATE TABLE `Product`
(
    `Id`         char(32) COLLATE utf8_unicode_ci    NOT NULL COMMENT '流水號',
    `ChannelId`  varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT 'Line 頻道 Id',
    `ProductId`  varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '產品 Id',
    `Name`       varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '產品名稱',
    `Price`      decimal(13, 4)                      DEFAULT NULL COMMENT '價格',
    `Quantity`   bigint(20) DEFAULT NULL COMMENT '數量',
    `Image`      text COLLATE utf8_unicode_ci        DEFAULT NULL COMMENT '圖片',
    `CreateTime` datetime                            DEFAULT NULL COMMENT '建立時間',
    `Creator`    varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '建立者',
    `UpdateTime` datetime                            DEFAULT NULL COMMENT '更新時間',
    `Updater`    varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='產品資料';

-- line.ScheduleJob definition

CREATE TABLE `ScheduleJob`
(
    `Id`             int(4) NOT NULL AUTO_INCREMENT,
    `JobName`        varchar(100) COLLATE utf8_unicode_ci NOT NULL,
    `JobGroup`       varchar(100) COLLATE utf8_unicode_ci NOT NULL,
    `JobDescription` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    `JobClass`       varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    `Status`         varchar(10) COLLATE utf8_unicode_ci  NOT NULL,
    `TimeUnit`       varchar(6) COLLATE utf8_unicode_ci   NOT NULL,
    `RepeatInterval` int(5) NOT NULL,
    `RepeatTimes`    int(5) NOT NULL,
    `StartTime`      datetime DEFAULT NULL,
    `EndTime`        datetime DEFAULT NULL,
    PRIMARY KEY (`Id`),
    UNIQUE KEY `JobName` (`JobName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- line.ScheduleJobLog definition

CREATE TABLE `ScheduleJobLog`
(
    `Id`             int(4) NOT NULL AUTO_INCREMENT,
    `JobName`        varchar(100) COLLATE utf8_unicode_ci NOT NULL,
    `JobDescription` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
    `Status`         varchar(10) COLLATE utf8_unicode_ci  NOT NULL,
    `StartTime`      datetime                     DEFAULT NULL,
    `EndTime`        datetime                     DEFAULT NULL,
    `Message`        text COLLATE utf8_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- line.SysMenu definition

CREATE TABLE `SysMenu`
(
    `MenuId`      int(5) NOT NULL COMMENT '主選單Id',
    `MenuName`    varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '選單名稱',
    `Path`        varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '選單路徑',
    `Comment`     varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '說明',
    `IsEnabled`   char(1) COLLATE utf8_unicode_ci      NOT NULL COMMENT '選單開關',
    `Sequence`    int(5) NOT NULL COMMENT '選單順序',
    `ParentId`    int(5) NOT NULL COMMENT '對應主選單Id',
    `create_time` datetime                             DEFAULT NULL COMMENT '建立時間',
    `create_user` varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '建立者',
    `update_time` datetime                             DEFAULT NULL COMMENT '更新時間',
    `update_user` varchar(50) COLLATE utf8_unicode_ci  DEFAULT NULL COMMENT '更新者',
    PRIMARY KEY (`MenuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='系統選單';
