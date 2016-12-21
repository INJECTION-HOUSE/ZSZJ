/**
 *拦截器 全局$http注入loading效果
 */
define(['jquery','angular'], function($, angular){
    angular.module('loading',[]).config(function($httpProvider) {
        $httpProvider.interceptors.push('loadingInterceptor');
    }).directive('loading', function() {
        return {
            replace: true,
            restrict: 'AE',
            template:'<div id="loadingDiv"><div id="loadContent"><img src="./images/load.gif"></div></div>',
            link: function($scope, $element, attrs) {
                console.log("loading div init");
            }
        };
    }).factory('loadingInterceptor', function($q) {
        return {
            request: function(config) {
                $("#loadingDiv").show();
                return config || $q.when(config);
            },
            response: function(response) {
                $("#loadingDiv").hide();
                return response || $q.when(response);
            },
            responseError: function(rejection) {
                $("#loadingDiv").hide();
                return $q.reject(rejection);
            }
        };
    });
});
