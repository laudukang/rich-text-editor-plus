/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/

KindEditor.plugin('insertfile', function(K) {
	var self = this, name = 'insertfile',
		allowFileUpload = K.undef(self.allowFileUpload, true),
		allowFileManager = K.undef(self.allowFileManager, false),
		formatUploadUrl = K.undef(self.formatUploadUrl, true),
		uploadJson = K.undef(self.uploadJson, self.basePath + 'jsp/upload_json.jsp'),//刘杜康 2014年10月13日16:00:05
		extraParams = K.undef(self.extraFileUploadParams, {}),
		filePostName = K.undef(self.filePostName, 'imgFile'),
		lang = self.lang(name + '.');
	self.plugin.fileDialog = function(options) {
		var fileUrl = K.undef(options.fileUrl, 'http://'),
			fileTitle = K.undef(options.fileTitle, ''),
			clickFn = options.clickFn;
		var html = [
			'<div style="padding:20px;">',
			'<div class="ke-dialog-row">',
			'<label for="keUrl" style="width:60px;">' + lang.url + '</label>',
			'<input type="text" id="keUrl" name="url" class="ke-input-text" style="width:160px;" /> &nbsp;',
			'<input type="button" class="ke-upload-button" value="' + lang.upload + '" /> &nbsp;',
			'<span class="ke-button-common ke-button-outer">',
			'<input type="button" class="ke-button-common ke-button" name="viewServer" value="' + lang.viewServer + '" />',
			'</span>',
			'</div>',
			//title
			'<div class="ke-dialog-row">',
			'<label for="keTitle" style="width:60px;">' + lang.title + '</label>',
			'<input type="text" id="keTitle" class="ke-input-text" name="title" value="" style="width:160px;" /></div>',
			'</div>',
			//form end
			'</form>',
			'</div>'
			].join('');
		var dialog = self.createDialog({
			name : name,
			width : 450,
			title : self.lang(name),
			body : html,
			yesBtn : {
				name : self.lang('yes'),
				click : function(e) {
					var url = K.trim(urlBox.val()),
						title = titleBox.val();
					if (url == 'http://' || K.invalidUrl(url)) {
						alert(self.lang('invalidUrl'));
						urlBox[0].focus();
						return;
					}
					if (K.trim(title) === '') {
						title = url;
					}
					clickFn.call(self, url, title);
				}
			}
		}),
		div = dialog.div;

		var urlBox = K('[name="url"]', div),
			viewServerBtn = K('[name="viewServer"]', div),
			titleBox = K('[name="title"]', div);

		if (allowFileUpload) {
			var uploadbutton = K.uploadbutton({
				button : K('.ke-upload-button', div)[0],
				fieldName : filePostName,
				url : K.addParam(uploadJson, 'dir=file'),
				extraParams : extraParams,
				afterUpload : function(data) {
					dialog.hideLoading();
					if (data.error === 0) {
						var url = data.url;
						var title = data.title;//刘杜康 2014年10月11日09:25:03
						if (formatUploadUrl) {
							url = K.formatUrl(url, 'absolute');
						}
						urlBox.val(url);
						titleBox.val(title);//刘杜康 2014年10月11日09:24:50
						if (self.afterUpload) {
							self.afterUpload.call(self, url, data, name);
						}
						//alert(self.lang('uploadSuccess'));//隐藏上传成功弹出框 刘杜康 2014年10月13日19:56:24
					} else {
						alert(data.message);
					}
				},
				afterError : function(html) {
					dialog.hideLoading();
					self.errorDialog(html);
				}
			});
			uploadbutton.fileBox.change(function(e) {
				dialog.showLoading(self.lang('uploadLoading'));
				uploadbutton.submit();
			});
		} else {
			K('.ke-upload-button', div).hide();
		}
		if (allowFileManager) {
			viewServerBtn.click(function(e) {
				self.loadPlugin('filemanager', function() {
					self.plugin.filemanagerDialog({
						viewType : 'LIST',
						dirName : 'file',
						clickFn : function(url, title) {
							if (self.dialogs.length > 1) {
								K('[name="url"]', div).val(url);
								if (self.afterSelectFile) {
									self.afterSelectFile.call(self, url);
								}
								self.hideDialog();
							}
						}
					});
				});
			});
		} else {
			viewServerBtn.hide();
		}
		urlBox.val(fileUrl);
		titleBox.val(fileTitle);
		urlBox[0].focus();
		urlBox[0].select();
	};
	self.clickToolbar(name, function() {
		self.plugin.fileDialog({
			clickFn : function(url, title) {
				//为了避免转义反斜杠出问题，这里将对其进行转换
				var re = /(\\+)/g; 
				var titleTemp=title.replace(re,"#");
				//对路径字符串进行剪切截取
				var oneT=titleTemp.split("#");
				//获取数组中最后一个，即文件名
				var twoT=oneT[oneT.length-1];
				//再对文件名进行截取，以取得后缀名
				var threeT=twoT.split(".");
				 //获取截取的最后一个字符串，即为后缀名
				var suffix=threeT[threeT.length-1];
				var imgT;
				/*var imgTypes = "jpg,gif,bmp,JPG,GIF,BMP,swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb";
				var rs=imgTypes.indexOf(suffix);
				var html;
				if(rs>=0){
				    html = '<a class="ke-insertfile" style="font-size: 12px;height:20px; line-height:20px;" href="' + url + '" data-ke-src="' + url + '" target="_blank">' + title + '</a>';	 
				}
				else{*/
				switch (suffix) {
	                case "doc":
	                    imgT = "plugins/image/images/word.png";
	                    break;
	                case "xls":
	                    imgT = "plugins/image/images/execl.png";
	                    break;
	                case "ppt":
	                    imgT = "plugins/image/images/ppt.png";
	                    break;
	                case "docx": 
	                    imgT = "plugins/image/images/word.png";
	                    break;
	                case "xlsx":
	                    imgT = "plugins/image/images/execl.png";
	                    break;
	                case "pptx":
	                    imgT = "plugins/image/images/ppt.png";
	                    break;
	                case "rar":
	                    imgT = "plugins/image/images/rar_zip.png";
	                    break;
	                case "zip":
	                    imgT = "plugins/image/images/rar_zip.png";
	                    break;
	                case "txt":
	                    imgT = "plugins/image/images/txt.png";
	                    break;
	                case "pdf":
	                    imgT = "plugins/image/images/pdf.png";
	                    break;
	                default:
	                    imgT = "plugins/image/images/file.png";
	            }
				html = '<img src="'+self.basePath+imgT+'" style="width:20px; height:20px; vertical-align:middle; border:0;">'+
				'<a class="ke-insertfile" style="font-size: 12px;height:20px; line-height:20px;" href="' + url + '" data-ke-src="' + url + '" target="_blank">' + title + '</a>';
				//}
				self.insertHtml(html).hideDialog().focus();
			}
		});
	});
});
