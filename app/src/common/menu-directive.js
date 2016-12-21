define(['jquery','angular'], function($, angular){
    angular.module('menu',[]).directive('menu', function($navigate) {
        return {
            restrict: 'AE',
            scope: {
                current: "@"
            },
            replace: true,
            link: function($scope, $element, attrs) {
                $scope.selectMenu = function(menu){
                    if(menu !== $scope.current){
                        $navigate.go(menu);
                    }
                };
            },
            templateUrl:'./common/menu-template.html'
        };
    });
});