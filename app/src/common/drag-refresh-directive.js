/**
 * Created by kaku on 16-11-13.
 */
define(['jquery','angular'], function($, angular){
    angular.module('drag-refresh',[]).directive('dragRefresh', function($location) {
        return {
            restrict: 'AE',
            scope: {
                hanlder: "&"
            },
            replace: true,
            link: function($scope, $element, attrs) {
                $scope.needHandle = false;
                $element.parent().bind("touchmove", function(e){
                    if($element.parent().scrollTop() > 20){
                        // 让 $dom 元素可以正常滚动
                        $(".drag-refresh").height(20);
                        $scope.needHandle = true;
                        return true;
                    }else{
                        $scope.needHandle = false;
                        return true;
                    }
                    console.log($element.parent().scrollTop());
                    // 阻止默认的滚动事件
                    e.preventDefault();
                });

                $element.parent().bind("touchend", function(e){
                    $(".drag-refresh").height(0);
                    if($scope.needHandle){
                        $scope.hanlder();
                    }
                });
            },
            template:'<div class="drag-refresh"> <div class="rect1"></div> <div class="rect2"></div> <div class="rect3"></div> <div class="rect4"></div> <div class="rect5"></div></div>'
        };
    });
});