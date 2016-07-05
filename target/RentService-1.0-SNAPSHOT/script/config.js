/*
 ckeditor config
 */

var requestContextPath = '/RentService'; 

CKEDITOR.editorConfig = function(config) {
	config.resize_enabled = true; 
        config.language = 'th'; 
        config.extraPlugins = 'image'; 
        config.filebrowserUploadUrl = requestContextPath + '/SimpleUploadServlet'  
        config.image_removeLinkByEmptyURL= true; 
        config.image_previewText = CKEDITOR.tools.repeat( 'ตัวอย่างรูปภาพ ', 1 );

        // Toolbar configuration generated automatically by the editor based on config.toolbarGroups.
        config.toolbar = [
                { name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source', '-', 'Save', 'NewPage', 'Preview', 'Print', '-', 'Templates' ] },
                { name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
                { name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Find', 'Replace', '-', 'SelectAll', '-', 'Scayt' ] },
                { name: 'forms', items: [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
                '/',
                { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
                { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl', 'Language' ] },
                { name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
                { name: 'insert', items: [ 'Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe' ] },
                '/',
                { name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
                { name: 'colors', items: [ 'TextColor', 'BGColor' ] },
                { name: 'tools', items: [ 'Maximize', 'ShowBlocks' ] },
                { name: 'others', items: [ '-' ] },
                { name: 'about', items: [ 'About' ] }
        ];

        // Toolbar groups configuration.
        config.toolbarGroups = [
                { name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
                { name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
                { name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ] },
                { name: 'forms' },
                '/',
                { name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
                { name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
                { name: 'links' },
                { name: 'insert' },
                '/',
                { name: 'styles' },
                { name: 'colors' },
                { name: 'tools' },
                { name: 'others' },
                { name: 'about' }
        ];        
        
        // Toolbar file browser. 
};
 
CKEDITOR.config.filebrowserBrowseUrl = requestContextPath + '/secured/ckeditor/browse.zul?Type=Images' ;
CKEDITOR.config.filebrowserImageBrowseUrl = requestContextPath + '/secured/ckeditor/browse.zul?Type=Images' ;
CKEDITOR.config.filebrowserImageBrowseLinkUrl  = '' ; 
CKEDITOR.config.filebrowserFlashBrowseUrl = requestContextPath + '/secured/ckeditor/browse.zul?Type=Flash' ;

CKEDITOR.config.filebrowserWindowFeatures = 'resizable=yes,scrollbars=no';
CKEDITOR.config.filebrowserWindowWidth = '70%'; 
CKEDITOR.config.filebrowserWindowHeight = '60%'; 

