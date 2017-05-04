define(['angular', 'menu'], function(angular){
    angular.module('shop-controller', ['menu']).controller('shopController', function($scope, $location, $sce){
        $scope.text = "商店商店";
//        $location.path('http://115.28.175.114/suran/wap');
        /*window.location.href = 'http://115.28.175.114/suran/wap/';*/
        $scope.trustSrc = $sce.trustAs($sce.RESOURCE_URL,"http://shop.zhusuhome.com/suran/wap");
    });
});