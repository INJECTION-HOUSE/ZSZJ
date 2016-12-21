define(['angular', 'menu'], function(angular){
    angular.module('uploadPortrait-controller', ['menu']).controller('uploadPortraitController', function($scope){
        $scope.text = "维修任务";

        $(".changePortraitDialog").mobiscroll().widget({
            theme: 'mobiscroll',
            lang: 'zh',
            display: 'bottom',
            anchor: '#show',
            cssClass: 'white_content',
            buttons: []
        });


        $scope.changePortrait = function() {
            $(".changePortraitDialog").mobiscroll('show');
        }

        $scope.changePortraitSelect = function(value) {
            $(".changePortraitDialog").mobiscroll('hide');
            console.log(value);
        }
    });
});