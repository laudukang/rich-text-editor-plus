/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.language = 'zh-cn';  
    config.filebrowserBrowseUrl = 'ckeditor/jsp/browse.jsp';  
    config.filebrowserImageBrowseUrl = 'ckeditor/jsp/browse.jsp?type=Images';  
    config.filebrowserFlashBrowseUrl = 'ckeditor/jsp/browse.jsp?type=Flashs';  
    config.filebrowserUploadUrl = 'ckeditor/jsp/upload.jsp';  
    config.filebrowserImageUploadUrl = 'ckeditor/jsp/upload.jsp?type=Images';  
    config.filebrowserFlashUploadUrl = 'ckeditor/jsp/upload.jsp?type=Flashs';  
    config.filebrowserWindowWidth = '640';  
    config.filebrowserWindowHeight = '480';  
};
