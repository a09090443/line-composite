-- Line 頻道
INSERT INTO line.LineChannel (ChannelId,ChannelSecret,BotId,Name,Description,Email,UserId,AccessToken,CreateTime,Creator,UpdateTime,Updater,LineStoreId) VALUES
('1655687092','87e63432d9cf467db49b73cf2885b0da','@548qthnr','Kotlin咖啡廳','測試 Line bot 功能','zipe.daden@gmail.com','U04c1968aa97516a3a9d22503dc2b6c0c','sKr6/E51tO2TB0eqK7C9+jIQaiQhExcyLyqxoOC7HfzEa0ZXnsBdRZhIuLDRxcZhr6MT08R2mdhrXqtYwxCAkXR5ZJB9h9XXEQ2R6efPhAkO7E4Iw4DTZdm43prYCrkEfsvelyBR0JMAFrVTa+X7BwdB04t89/1O/w1cDnyilFU=',NULL,NULL,NULL,NULL,'1654394737');

-- Line 商店
INSERT INTO LineStore (ChannelId, ChannelSecret, Name, Description, Email, CreateTime, Creator, UpdateTime, Updater) VALUES('1654394737', '3dc8c4282cf38fb050110542f973d5a7', 'Line Pay Test', 'Line pay 測試用', 'zipe.daden@gmail.com', NULL, NULL, NULL, NULL);

-- Line 使用者
INSERT INTO LineInfo (LineId, Name, StatusMessage, `Type`, Status, CreateTime, Creator, UpdateTime, Updater) VALUES('U04c1968aa97516a3a9d22503dc2b6c0c', '天賜良機', '我不想努力了', 'USER', '', '2021-01-30 22:02:03.000', '', '2021-01-30 22:02:03.000', '');

-- Line 頻道對應 Line 使用者
INSERT INTO LineMapping (ChannelId, InfoId) VALUES('1655687092', 'U04c1968aa97516a3a9d22503dc2b6c0c');

-- 訊息關鍵字
INSERT INTO MessageSetting (Id, Name, Comment, CreateTime, Creator, UpdateTime, Updater) VALUES('c3de801e541011ebad5a0242ac1d0402', '我是誰', NULL, NULL, NULL, NULL, NULL);
INSERT INTO MessageSetting (Id, Name, Comment, CreateTime, Creator, UpdateTime, Updater) VALUES('c3de94f9541011ebad5a0242ac1d0402', '選單', NULL, NULL, NULL, NULL, NULL);
INSERT INTO MessageSetting (Id, Name, Comment, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177a65b2a0177a65c54660000', '正妹', '', '2021-02-15 23:43:24.000', '', '2021-02-15 23:43:24.000', '');

-- 訊息回覆內容
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6049c70000', '{"type":"image","originalContentUrl":"https://i.imgur.com/ZdolZIO.jpg","previewImageUrl":"https://i.imgur.com/ZdolZIO.jpg"}', 'image', '', '2021-02-19 21:00:08.000', '', '2021-02-19 21:00:08.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba604d360001', '{"type":"image","originalContentUrl":"https://i.imgur.com/g1l6Qv2.jpg","previewImageUrl":"https://i.imgur.com/g1l6Qv2.jpg"}', 'image', '', '2021-02-19 21:00:09.000', '', '2021-02-19 21:00:09.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba604ec80002', '{"type":"image","originalContentUrl":"https://i.imgur.com/HOK5YRD.jpg","previewImageUrl":"https://i.imgur.com/HOK5YRD.jpg"}', 'image', '', '2021-02-19 21:00:09.000', '', '2021-02-19 21:00:09.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6051b40003', '{"type":"image","originalContentUrl":"https://i.imgur.com/SgOZueA.jpg","previewImageUrl":"https://i.imgur.com/SgOZueA.jpg"}', 'image', '', '2021-02-19 21:00:10.000', '', '2021-02-19 21:00:10.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba60536e0004', '{"type":"image","originalContentUrl":"https://i.imgur.com/kLcDNx0.jpg","previewImageUrl":"https://i.imgur.com/kLcDNx0.jpg"}', 'image', '', '2021-02-19 21:00:10.000', '', '2021-02-19 21:00:10.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6055f00005', '{"type":"image","originalContentUrl":"https://i.imgur.com/yx0lkqT.jpg","previewImageUrl":"https://i.imgur.com/yx0lkqT.jpg"}', 'image', '', '2021-02-19 21:00:11.000', '', '2021-02-19 21:00:11.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6058cd0006', '{"type":"image","originalContentUrl":"https://i.imgur.com/y0HUW21.jpg","previewImageUrl":"https://i.imgur.com/y0HUW21.jpg"}', 'image', '', '2021-02-19 21:00:12.000', '', '2021-02-19 21:00:12.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba605ac80007', '{"type":"image","originalContentUrl":"https://i.imgur.com/Xs6Muf7.jpg","previewImageUrl":"https://i.imgur.com/Xs6Muf7.jpg"}', 'image', '', '2021-02-19 21:00:12.000', '', '2021-02-19 21:00:12.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba605cd00008', '{"type":"image","originalContentUrl":"https://i.imgur.com/JKk8dd8.jpg","previewImageUrl":"https://i.imgur.com/JKk8dd8.jpg"}', 'image', '', '2021-02-19 21:00:13.000', '', '2021-02-19 21:00:13.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6060d70009', '{"type":"image","originalContentUrl":"https://i.imgur.com/mnEk7kW.jpg","previewImageUrl":"https://i.imgur.com/mnEk7kW.jpg"}', 'image', '', '2021-02-19 21:00:14.000', '', '2021-02-19 21:00:14.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba60623c000a', '{"type":"image","originalContentUrl":"https://i.imgur.com/XfAU4WP.jpg","previewImageUrl":"https://i.imgur.com/XfAU4WP.jpg"}', 'image', '', '2021-02-19 21:00:14.000', '', '2021-02-19 21:00:14.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba606475000b', '{"type":"image","originalContentUrl":"https://i.imgur.com/R5nOp5o.jpg","previewImageUrl":"https://i.imgur.com/R5nOp5o.jpg"}', 'image', '', '2021-02-19 21:00:15.000', '', '2021-02-19 21:00:15.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6065fd000c', '{"type":"image","originalContentUrl":"https://i.imgur.com/Q90s2ub.jpg","previewImageUrl":"https://i.imgur.com/Q90s2ub.jpg"}', 'image', '', '2021-02-19 21:00:15.000', '', '2021-02-19 21:00:15.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba606747000d', '{"type":"image","originalContentUrl":"https://i.imgur.com/6f2EFDL.jpg","previewImageUrl":"https://i.imgur.com/6f2EFDL.jpg"}', 'image', '', '2021-02-19 21:00:16.000', '', '2021-02-19 21:00:16.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6068c4000e', '{"type":"image","originalContentUrl":"https://i.imgur.com/fBOo5md.jpg","previewImageUrl":"https://i.imgur.com/fBOo5md.jpg"}', 'image', '', '2021-02-19 21:00:16.000', '', '2021-02-19 21:00:16.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba606a9d000f', '{"type":"image","originalContentUrl":"https://i.imgur.com/C44sQab.jpg","previewImageUrl":"https://i.imgur.com/C44sQab.jpg"}', 'image', '', '2021-02-19 21:00:16.000', '', '2021-02-19 21:00:16.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba606bf00010', '{"type":"image","originalContentUrl":"https://i.imgur.com/JEYLeRY.jpg","previewImageUrl":"https://i.imgur.com/JEYLeRY.jpg"}', 'image', '', '2021-02-19 21:00:17.000', '', '2021-02-19 21:00:17.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba606d980011', '{"type":"image","originalContentUrl":"https://i.imgur.com/ogChz31.jpg","previewImageUrl":"https://i.imgur.com/ogChz31.jpg"}', 'image', '', '2021-02-19 21:00:17.000', '', '2021-02-19 21:00:17.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6070840012', '{"type":"image","originalContentUrl":"https://i.imgur.com/j1wgDE8.jpg","previewImageUrl":"https://i.imgur.com/j1wgDE8.jpg"}', 'image', '', '2021-02-19 21:00:18.000', '', '2021-02-19 21:00:18.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6076180013', '{"type":"image","originalContentUrl":"https://i.imgur.com/BT3UyFw.jpg","previewImageUrl":"https://i.imgur.com/BT3UyFw.jpg"}', 'image', '', '2021-02-19 21:00:19.000', '', '2021-02-19 21:00:19.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba607ee60014', '{"type":"image","originalContentUrl":"https://i.imgur.com/JXCLRGK.jpg","previewImageUrl":"https://i.imgur.com/JXCLRGK.jpg"}', 'image', '', '2021-02-19 21:00:21.000', '', '2021-02-19 21:00:21.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6085f60015', '{"type":"image","originalContentUrl":"https://i.imgur.com/N6rPVhg.jpg","previewImageUrl":"https://i.imgur.com/N6rPVhg.jpg"}', 'image', '', '2021-02-19 21:00:23.000', '', '2021-02-19 21:00:23.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba608a5d0016', '{"type":"image","originalContentUrl":"https://i.imgur.com/KjXTH7m.jpg","previewImageUrl":"https://i.imgur.com/KjXTH7m.jpg"}', 'image', '', '2021-02-19 21:00:24.000', '', '2021-02-19 21:00:24.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba608de20017', '{"type":"image","originalContentUrl":"https://i.imgur.com/pEhk48k.jpg","previewImageUrl":"https://i.imgur.com/pEhk48k.jpg"}', 'image', '', '2021-02-19 21:00:25.000', '', '2021-02-19 21:00:25.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6091120018', '{"type":"image","originalContentUrl":"https://i.imgur.com/pNn2rdB.jpg","previewImageUrl":"https://i.imgur.com/pNn2rdB.jpg"}', 'image', '', '2021-02-19 21:00:26.000', '', '2021-02-19 21:00:26.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6092d70019', '{"type":"image","originalContentUrl":"https://i.imgur.com/UK1tdKa.jpg","previewImageUrl":"https://i.imgur.com/UK1tdKa.jpg"}', 'image', '', '2021-02-19 21:00:27.000', '', '2021-02-19 21:00:27.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba60946c001a', '{"type":"image","originalContentUrl":"https://i.imgur.com/gdmYQAy.jpg","previewImageUrl":"https://i.imgur.com/gdmYQAy.jpg"}', 'image', '', '2021-02-19 21:00:27.000', '', '2021-02-19 21:00:27.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba60978b001b', '{"type":"image","originalContentUrl":"https://i.imgur.com/VLrzZu4.jpg","previewImageUrl":"https://i.imgur.com/VLrzZu4.jpg"}', 'image', '', '2021-02-19 21:00:28.000', '', '2021-02-19 21:00:28.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba609958001c', '{"type":"image","originalContentUrl":"https://imgur.com/mKglw6F.jpg","previewImageUrl":"https://imgur.com/mKglw6F.jpg"}', 'image', '', '2021-02-19 21:00:28.000', '', '2021-02-19 21:00:28.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba609b62001d', '{"type":"image","originalContentUrl":"https://imgur.com/YaRooor.jpg","previewImageUrl":"https://imgur.com/YaRooor.jpg"}', 'image', '', '2021-02-19 21:00:29.000', '', '2021-02-19 21:00:29.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba609cc9001e', '{"type":"image","originalContentUrl":"https://imgur.com/TmmzYOJ.jpg","previewImageUrl":"https://imgur.com/TmmzYOJ.jpg"}', 'image', '', '2021-02-19 21:00:29.000', '', '2021-02-19 21:00:29.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba609e0f001f', '{"type":"image","originalContentUrl":"https://imgur.com/iKuidMX.jpg","previewImageUrl":"https://imgur.com/iKuidMX.jpg"}', 'image', '', '2021-02-19 21:00:30.000', '', '2021-02-19 21:00:30.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba60a0420020', '{"type":"image","originalContentUrl":"https://imgur.com/blHsYU8.jpg","previewImageUrl":"https://imgur.com/blHsYU8.jpg"}', 'image', '', '2021-02-19 21:00:30.000', '', '2021-02-19 21:00:30.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba62f9580021', '{"type":"image","originalContentUrl":"https://i.imgur.com/ZdolZIO.jpg","previewImageUrl":"https://i.imgur.com/ZdolZIO.jpg"}', 'image', '', '2021-02-19 21:03:04.000', '', '2021-02-19 21:03:04.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba62fe7a0022', '{"type":"image","originalContentUrl":"https://i.imgur.com/g1l6Qv2.jpg","previewImageUrl":"https://i.imgur.com/g1l6Qv2.jpg"}', 'image', '', '2021-02-19 21:03:05.000', '', '2021-02-19 21:03:05.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6300a70023', '{"type":"image","originalContentUrl":"https://i.imgur.com/HOK5YRD.jpg","previewImageUrl":"https://i.imgur.com/HOK5YRD.jpg"}', 'image', '', '2021-02-19 21:03:06.000', '', '2021-02-19 21:03:06.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6302020024', '{"type":"image","originalContentUrl":"https://i.imgur.com/SgOZueA.jpg","previewImageUrl":"https://i.imgur.com/SgOZueA.jpg"}', 'image', '', '2021-02-19 21:03:06.000', '', '2021-02-19 21:03:06.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6303840025', '{"type":"image","originalContentUrl":"https://i.imgur.com/kLcDNx0.jpg","previewImageUrl":"https://i.imgur.com/kLcDNx0.jpg"}', 'image', '', '2021-02-19 21:03:07.000', '', '2021-02-19 21:03:07.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba63063a0026', '{"type":"image","originalContentUrl":"https://i.imgur.com/yx0lkqT.jpg","previewImageUrl":"https://i.imgur.com/yx0lkqT.jpg"}', 'image', '', '2021-02-19 21:03:07.000', '', '2021-02-19 21:03:07.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6308a00027', '{"type":"image","originalContentUrl":"https://i.imgur.com/y0HUW21.jpg","previewImageUrl":"https://i.imgur.com/y0HUW21.jpg"}', 'image', '', '2021-02-19 21:03:08.000', '', '2021-02-19 21:03:08.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba630aa70028', '{"type":"image","originalContentUrl":"https://i.imgur.com/Xs6Muf7.jpg","previewImageUrl":"https://i.imgur.com/Xs6Muf7.jpg"}', 'image', '', '2021-02-19 21:03:08.000', '', '2021-02-19 21:03:08.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba630c7d0029', '{"type":"image","originalContentUrl":"https://i.imgur.com/JKk8dd8.jpg","previewImageUrl":"https://i.imgur.com/JKk8dd8.jpg"}', 'image', '', '2021-02-19 21:03:09.000', '', '2021-02-19 21:03:09.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba630ded002a', '{"type":"image","originalContentUrl":"https://i.imgur.com/mnEk7kW.jpg","previewImageUrl":"https://i.imgur.com/mnEk7kW.jpg"}', 'image', '', '2021-02-19 21:03:09.000', '', '2021-02-19 21:03:09.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba630f40002b', '{"type":"image","originalContentUrl":"https://i.imgur.com/XfAU4WP.jpg","previewImageUrl":"https://i.imgur.com/XfAU4WP.jpg"}', 'image', '', '2021-02-19 21:03:10.000', '', '2021-02-19 21:03:10.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6310bf002c', '{"type":"image","originalContentUrl":"https://i.imgur.com/R5nOp5o.jpg","previewImageUrl":"https://i.imgur.com/R5nOp5o.jpg"}', 'image', '', '2021-02-19 21:03:10.000', '', '2021-02-19 21:03:10.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba631315002d', '{"type":"image","originalContentUrl":"https://i.imgur.com/Q90s2ub.jpg","previewImageUrl":"https://i.imgur.com/Q90s2ub.jpg"}', 'image', '', '2021-02-19 21:03:11.000', '', '2021-02-19 21:03:11.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba631491002e', '{"type":"image","originalContentUrl":"https://i.imgur.com/6f2EFDL.jpg","previewImageUrl":"https://i.imgur.com/6f2EFDL.jpg"}', 'image', '', '2021-02-19 21:03:11.000', '', '2021-02-19 21:03:11.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba631693002f', '{"type":"image","originalContentUrl":"https://i.imgur.com/fBOo5md.jpg","previewImageUrl":"https://i.imgur.com/fBOo5md.jpg"}', 'image', '', '2021-02-19 21:03:11.000', '', '2021-02-19 21:03:11.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba63194c0030', '{"type":"image","originalContentUrl":"https://i.imgur.com/C44sQab.jpg","previewImageUrl":"https://i.imgur.com/C44sQab.jpg"}', 'image', '', '2021-02-19 21:03:12.000', '', '2021-02-19 21:03:12.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba631c6b0031', '{"type":"image","originalContentUrl":"https://i.imgur.com/JEYLeRY.jpg","previewImageUrl":"https://i.imgur.com/JEYLeRY.jpg"}', 'image', '', '2021-02-19 21:03:13.000', '', '2021-02-19 21:03:13.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba631ffd0032', '{"type":"image","originalContentUrl":"https://i.imgur.com/ogChz31.jpg","previewImageUrl":"https://i.imgur.com/ogChz31.jpg"}', 'image', '', '2021-02-19 21:03:14.000', '', '2021-02-19 21:03:14.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6322010033', '{"type":"image","originalContentUrl":"https://i.imgur.com/j1wgDE8.jpg","previewImageUrl":"https://i.imgur.com/j1wgDE8.jpg"}', 'image', '', '2021-02-19 21:03:14.000', '', '2021-02-19 21:03:14.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6324390034', '{"type":"image","originalContentUrl":"https://i.imgur.com/BT3UyFw.jpg","previewImageUrl":"https://i.imgur.com/BT3UyFw.jpg"}', 'image', '', '2021-02-19 21:03:15.000', '', '2021-02-19 21:03:15.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba63266d0035', '{"type":"image","originalContentUrl":"https://i.imgur.com/JXCLRGK.jpg","previewImageUrl":"https://i.imgur.com/JXCLRGK.jpg"}', 'image', '', '2021-02-19 21:03:15.000', '', '2021-02-19 21:03:15.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6329630036', '{"type":"image","originalContentUrl":"https://i.imgur.com/N6rPVhg.jpg","previewImageUrl":"https://i.imgur.com/N6rPVhg.jpg"}', 'image', '', '2021-02-19 21:03:16.000', '', '2021-02-19 21:03:16.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba632b050037', '{"type":"image","originalContentUrl":"https://i.imgur.com/KjXTH7m.jpg","previewImageUrl":"https://i.imgur.com/KjXTH7m.jpg"}', 'image', '', '2021-02-19 21:03:17.000', '', '2021-02-19 21:03:17.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba632c590038', '{"type":"image","originalContentUrl":"https://i.imgur.com/pEhk48k.jpg","previewImageUrl":"https://i.imgur.com/pEhk48k.jpg"}', 'image', '', '2021-02-19 21:03:17.000', '', '2021-02-19 21:03:17.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba632dae0039', '{"type":"image","originalContentUrl":"https://i.imgur.com/pNn2rdB.jpg","previewImageUrl":"https://i.imgur.com/pNn2rdB.jpg"}', 'image', '', '2021-02-19 21:03:17.000', '', '2021-02-19 21:03:17.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba632f52003a', '{"type":"image","originalContentUrl":"https://i.imgur.com/UK1tdKa.jpg","previewImageUrl":"https://i.imgur.com/UK1tdKa.jpg"}', 'image', '', '2021-02-19 21:03:18.000', '', '2021-02-19 21:03:18.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6330c6003b', '{"type":"image","originalContentUrl":"https://i.imgur.com/gdmYQAy.jpg","previewImageUrl":"https://i.imgur.com/gdmYQAy.jpg"}', 'image', '', '2021-02-19 21:03:18.000', '', '2021-02-19 21:03:18.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba633249003c', '{"type":"image","originalContentUrl":"https://i.imgur.com/VLrzZu4.jpg","previewImageUrl":"https://i.imgur.com/VLrzZu4.jpg"}', 'image', '', '2021-02-19 21:03:19.000', '', '2021-02-19 21:03:19.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba633446003d', '{"type":"image","originalContentUrl":"https://imgur.com/mKglw6F.jpg","previewImageUrl":"https://imgur.com/mKglw6F.jpg"}', 'image', '', '2021-02-19 21:03:19.000', '', '2021-02-19 21:03:19.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6335c1003e', '{"type":"image","originalContentUrl":"https://imgur.com/YaRooor.jpg","previewImageUrl":"https://imgur.com/YaRooor.jpg"}', 'image', '', '2021-02-19 21:03:19.000', '', '2021-02-19 21:03:19.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba633786003f', '{"type":"image","originalContentUrl":"https://imgur.com/TmmzYOJ.jpg","previewImageUrl":"https://imgur.com/TmmzYOJ.jpg"}', 'image', '', '2021-02-19 21:03:20.000', '', '2021-02-19 21:03:20.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba6339350040', '{"type":"image","originalContentUrl":"https://imgur.com/iKuidMX.jpg","previewImageUrl":"https://imgur.com/iKuidMX.jpg"}', 'image', '', '2021-02-19 21:03:20.000', '', '2021-02-19 21:03:20.000', '');
INSERT INTO MessageDetail (Id, Value, `Type`, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES('ff80818177ba5fb20177ba633b090041', '{"type":"image","originalContentUrl":"https://imgur.com/blHsYU8.jpg","previewImageUrl":"https://imgur.com/blHsYU8.jpg"}', 'image', '', '2021-02-19 21:03:21.000', '', '2021-02-19 21:03:21.000', '');

-- 訊息關鍵字對應內容
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6049c70000');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba604d360001');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba604ec80002');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6051b40003');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba60536e0004');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6055f00005');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6058cd0006');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba605ac80007');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba605cd00008');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6060d70009');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba60623c000a');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba606475000b');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6065fd000c');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba606747000d');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6068c4000e');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba606a9d000f');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba606bf00010');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba606d980011');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6070840012');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6076180013');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba607ee60014');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6085f60015');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba608a5d0016');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba608de20017');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6091120018');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba6092d70019');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba60946c001a');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba60978b001b');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba609958001c');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba609b62001d');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba609cc9001e');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba609e0f001f');
INSERT INTO MessageMapping (MessageId, DetailId) VALUES('ff80818177a65b2a0177a65c54660000', 'ff80818177ba5fb20177ba60a0420020');

-- 購買流程
INSERT INTO OrderProcess (Id, Name, Content, `Type`, Enabled, `Sequence`, ParentId, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES(1, '美式咖啡', '{
  "type": "text",
  "text": "請輸入數量"
}', 'text', 'Y', 1, 0, '1655687092', NULL, NULL, NULL, NULL);
INSERT INTO OrderProcess (Id, Name, Content, `Type`, Enabled, `Sequence`, ParentId, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES(2, '確認', '{
  "type": "template",
  "altText": "確認付款",
  "template": {
    "type": "confirm",
    "actions": [
      {
        "type": "postback",
        "label": "是",
        "data": "{\"isToPay\":true,\"productId\":\"americano\",\"count\":\"%s\",\"quantityUnit\":\"quantity\"}"
      },
      {
        "type": "postback",
        "label": "否",
        "data": "{\"isCancel\":true}"
      }
    ],
    "text": "您所輸入的數量為%s杯，是否確認購買?"
  }
}', 'number', 'Y', 2, 1, '1655687092', NULL, NULL, NULL, NULL);
INSERT INTO OrderProcess (Id, Name, Content, `Type`, Enabled, `Sequence`, ParentId, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES(6, 'cancel', '{
  "type": "template",
  "altText": "取消購買流程",
  "template": {
    "type": "confirm",
    "actions": [
      {
        "type": "postback",
        "label": "取消流程",
        "data": "cancel_process"
      },
      {
        "type": "postback",
        "label": "繼續流程",
        "data": "continue_process"
      }
    ],
    "text": "取消購買流程"
  }
}', 'text', 'Y', 1, 0, '1654463046', NULL, NULL, NULL, NULL);
INSERT INTO OrderProcess (Id, Name, Content, `Type`, Enabled, `Sequence`, ParentId, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES(7, 'cancel_success', '{
  "type": "text",
  "text": "已完成取消購買"
}', 'text', 'Y', 1, 0, '1654463046', NULL, NULL, NULL, NULL);
INSERT INTO OrderProcess (Id, Name, Content, `Type`, Enabled, `Sequence`, ParentId, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES(8, 'pay', '{
  "type": "template",
  "altText": "點擊付款",
  "template": {
    "type": "buttons",
    "actions": [
      {
        "type": "uri",
        "label": "點擊付款",
        "uri": "%s"
      }
    ],
    "thumbnailImageUrl": "%s",
    "title": "Line Pay 線上付款",
    "text": "%s"
  }
}', 'text', 'Y', 1, 0, '1654463046', NULL, NULL, NULL, NULL);
INSERT INTO OrderProcess (Id, Name, Content, `Type`, Enabled, `Sequence`, ParentId, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES(9, 'pay_success', '{
  "type": "text",
  "text": "交易成功，謝謝您的購買。"
}', 'text', 'Y', 1, 0, '1654463046', NULL, NULL, NULL, NULL);
INSERT INTO OrderProcess (Id, Name, Content, `Type`, Enabled, `Sequence`, ParentId, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES(11, '確認', '{
  "type": "template",
  "altText": "確認付款",
  "template": {
    "type": "confirm",
    "actions": [
      {
        "type": "postback",
        "label": "是",
        "data": "{\"isToPay\":true,\"productId\":\"eerdfsdgww34\",\"count\":\"%s\",\"quantityUnit\":\"quantity\"}"
      },
      {
        "type": "postback",
        "label": "否",
        "data": "{\"isCancel\":true}"
      }
    ],
    "text": "您所輸入的數量為%s碗，是否確認購買?"
  }
}', 'text', 'Y', 2, 10, '1654463046', NULL, NULL, NULL, NULL);
INSERT INTO OrderProcess (Id, Name, Content, `Type`, Enabled, `Sequence`, ParentId, ChannelId, CreateTime, Creator, UpdateTime, Updater) VALUES(12, 'error', '{
  "type": "text",
  "text": "產生 Line Pay 錯誤，已取消交易流程"
}', 'text', 'Y', 1, 0, '1654463046', NULL, NULL, NULL, NULL);

-- 產品
INSERT INTO Product (Id, ChannelId, ProductId, Name, Price, Quantity, Image, CreateTime, Creator, UpdateTime, Updater) VALUES('c3de9257541011ebad5a0242ac1d04d2', '1655687092', 'americano', '美式咖啡', 50.0000, 100, 'https://www.caffebene.com.tw/upload/202001031715491.png', NULL, NULL, NULL, NULL);
INSERT INTO Product (Id, ChannelId, ProductId, Name, Price, Quantity, Image, CreateTime, Creator, UpdateTime, Updater) VALUES('fwde92575410113wad5a0242ac1d04d2', '1655687092', 'cappucino', '卡布其諾咖啡', 80.0000, 100, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Product (Id, ChannelId, ProductId, Name, Price, Quantity, Image, CreateTime, Creator, UpdateTime, Updater) VALUES('fwde9257541011ebad5a0242ac1d04d2', '1655687092', 'latte', '拿鐵咖啡', 60.0000, 100, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Product (Id, ChannelId, ProductId, Name, Price, Quantity, Image, CreateTime, Creator, UpdateTime, Updater) VALUES('fwde925d5410113wad5a0242ac1d04d2', '1655687092', 'mocha', '義式摩卡', 80.0000, 100, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Product (Id, ChannelId, ProductId, Name, Price, Quantity, Image, CreateTime, Creator, UpdateTime, Updater) VALUES('fwdeb25d5410113wad5a0242ac1d04d2', '1655687092', 'steamer', '牛奶', 50.0000, 100, NULL, NULL, NULL, NULL, NULL);
INSERT INTO Product (Id, ChannelId, ProductId, Name, Price, Quantity, Image, CreateTime, Creator, UpdateTime, Updater) VALUES('qwde925d5410113wad5a0242ac1d04d2', '1655687092', 'lemonade', '檸檬水', 5.0000, 100, NULL, NULL, NULL, NULL, NULL);
