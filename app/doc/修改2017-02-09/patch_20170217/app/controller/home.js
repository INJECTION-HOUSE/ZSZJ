define(['angular', 'menu'], function(angular){
    angular.module('home-controller', ['menu']).controller('homeController', function($scope, $navigate){
        $scope.text = "主页主页";
        $scope.goTask = function(type){
            $navigate.go('/addOrder/' + type);
        };
        $scope.goPage = function(page){
            $navigate.go('/' + page);
        };
    });
});