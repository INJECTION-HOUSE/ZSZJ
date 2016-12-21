/**
 * 页面自适应
 */
!(function(win) {
    var doc = win.document;
    var docEl = doc.documentElement;
    var tid;

    function refreshRem() {
        var width = docEl.getBoundingClientRect().width;
//        if(width>720){
//            width=720;
//        }
        var rem = width /4.14; // 将屏幕宽度分成10份， 1份为1rem
        docEl.style.fontSize = rem + 'px';
        //alert(docEl.style.fontSize);
    }
    win.addEventListener('resize', function() {
        clearTimeout(tid);
        tid = setTimeout(refreshRem, 300);
    }, false);
    win.addEventListener('pageshow', function(e) {
        if (e.persisted) {
            clearTimeout(tid);
            tid = setTimeout(refreshRem, 300);
        }
    }, false);
    refreshRem();
})(window);


require.config({
    baseUrl: "",
	paths: {
		"jquery": './vendor/jquery-1.11.1.min',
        "angular" : "./vendor/angular.min",
        //"angular" : "http://cdn.bootcss.com/angular.js/1.5.8/angular",
        "angularroute" : "./vendor/angular-route.min",
        "mobiscroll" : "./vendor/mobiscroll/mobiscroll.custom-2.17.0.min",
        "loading" : "./common/loading",
        "menu" : "./common/menu-directive",
        "dragRefresh" : "./common/drag-refresh-directive"
	},
    shim: {
        'jquery': {
            exports: ['$', 'jQuery']
        },
        'angular': {
            deps: ['jquery'],
            exports: 'angular'
        },
        'angularroute':{
            deps: ["angular"],
            exports: 'angularroute'
        },
        'mobiscroll' : {
            deps: ['jquery'],
            exports: 'mobiscroll'
        },
        'loading' : {
            deps: ['jquery', 'angular'],
            exports: 'loading'
        },
        'menu' : {
            deps: ['jquery', 'angular'],
            exports: 'menu'
        },
        'dragRefresh' : {
            deps: ['jquery', 'angular'],
            exports: 'dragRefresh'
        }
    },
    waitSeconds: 0,
    //urlArgs: "v=" + (new Date()).getTime()
    urlArgs: "v=20160928"
});

require(['angular', 'app'], function (angular, app) {
    angular.bootstrap(document,['app']);
});
