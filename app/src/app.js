define(['angular', 'angularroute', 'controller/index', 'controller/home', 'controller/task', 'controller/shop', 'controller/pc', 'controller/changeInfo', 'controller/uploadPortrait', 'controller/gainRecord', 'controller/bidOrder', 'controller/addOrder',
    'controller/taskDetail', 'controller/bidRecord', 'controller/repairTask', 'controller/taskJoiner', 'controller/taskBid', 'controller/bindUser'], function(angular){
    var app = angular.module('app', ['ngRoute', 'index-controller', 'home-controller', 'task-controller', 'shop-controller', 'pc-controller', 'changeInfo-controller', 'uploadPortrait-controller', 'gainRecord-controller', 'bidOrder-controller', 'add-order-controller',
        'task-detail-controller', 'bidRecord-controller', 'repairTask-controller', 'task-joiner-controller', 'task-bid-controller', 'bind-user-controller']);
    var server = "http://localhost:8080/zszj";
        /**
         * 控制页面回退时使用反向动画
         */
    app.directive('backAnimation', ['$browser', '$location', function ($browser, $location) {
        return {
            link: function (scope, element) {
                $browser.onUrlChange(function (newUrl) {
                    if ($location.absUrl() === newUrl) {
                        element.addClass('reverse');
                    }
                });

                scope.__childrenCount = 0;
                scope.$watch(function () {
                    scope.__childrenCount = element.children().length;
                });

                scope.$watch('__childrenCount', function (newCount, oldCount) {
                    if (newCount !== oldCount && newCount === 1) {
                        element.removeClass('reverse');
                    }
                });
            }
        };
    }])

    /**
     * 页面跳转(go)和后退(back)
     * isReplace 为true时表示跳转到下个页面后，前个页面不记录在历史记录中
     * search表示传入到下个页面的参数，如'backurl=/home'
     */
    .provider('$navigate', function () {
        this.$get = ['$rootScope', '$location', '$window', function ($rootScope, $location, $window) {
            var nav = {};
            nav.go = function go(path, isReplace, search) {

                if (isReplace) {
                    $location.replace();
                }
                if (search) {
                    $location.search(search.split('=')[0], search.split('=')[1]);
                } else {
                    //delete $location.$$search.nameOfParameter;
                    $location.$$search = {};
                }
                $location.path(path);
            };

            nav.back = function () {
                $window.history.back();
            };

            nav.param = function(name, url) {
                if(!url){
                    url = window.location.search;
                }
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
                var r = url.substr(1).match(reg);
                if (r != null) return unescape(r[2]); return null;
            };
            return nav;
        }];
    })
    .provider('$ajax', function () {
        this.$get = ['$window', function ($window) {
            var obj = {};
            obj.get = function(options){
                $.ajax({
                    url: server + options.url,
                    data: options.data,
                    method: 'GET',
                    async: false,
                    dataType:"json",
                    success: function(d){
                        options.success(d);
                    },
                    error: function(e){
                        if(e.status == 200){
                            options.success(true);
                        }else{
                            options.error ? options.error(e) : '';
                        }
                    }
                });
            };
            obj.post = function(options){
                $.ajax({
                    url: server + options.url,
                    data: options.data,
                    method: 'POST',
                    async: false,
                    dataType:"json",
                    success: function(d){
                        options.success(d);
                    },
                    error: function(e){
                        if(e.status == 200){
                            options.success(true);
                        }else{
                            options.error ? options.error(e) : '';
                        }
                    }
                });
            };
            return obj;
        }];
    })
    .config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
        $routeProvider.when('/', {
            templateUrl: 'view/home.html',
            controller: "homeController"
        }).when('/home', {
            templateUrl: 'view/home.html',
            controller: "homeController"
        }).when('/task', {
            templateUrl: 'view/task.html',
            controller: "taskController"
        }).when('/shop', {
            templateUrl: 'view/shop.html',
            controller: "shopController"
        }).when('/pc', {
            templateUrl: 'view/pc.html',
            controller: "pcController"
        }).when('/changeInfo', {
            templateUrl: 'view/changeInfo.html',
            controller: "changeInfoController"
        }).when('/gainRecord', {
            templateUrl: 'view/gainRecord.html',
            controller: "gainRecordController"
        }).when('/addOrder', {
            templateUrl: 'view/addOrder.html',
            controller: "addOrderController"
        }).when('/uploadPortrait', {
            templateUrl: 'view/uploadPortrait.html',
            controller: "uploadPortraitController"
        }).when('/bidOrder', {
            templateUrl: 'view/bidOrder.html',
            controller: "bidOrderController"
        }).when('/taskDetail/:id', {
            templateUrl: 'view/taskDetail.html',
            controller: "taskDetailController"
        }).when('/bidRecord', {
            templateUrl: 'view/bidRecord.html',
            controller: 'bidRecordController'
        }).when('/repairTask', {
            templateUrl: 'view/repairTask.html',
            controller: 'repairTaskController'
        }).when('/taskJoiner/:id', {
            templateUrl: 'view/taskJoiner.html',
            controller: 'taskJoinerController'
        }).when('/taskBid/:id', {
            templateUrl: 'view/taskBid.html',
            controller: 'taskBidController'
        }).when('/bindUser', {
            templateUrl: 'view/bindUser.html',
            controller: 'bindUserController'
        });

        $locationProvider.html5Mode(true);
    }]);

    return app;
})