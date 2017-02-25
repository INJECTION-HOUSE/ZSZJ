define(['angular'], function(angular){
    angular.module('task-detail-controller', ['menu']).controller('taskDetailController', function($scope, $rootScope, $routeParams, $navigate, $ajax){
        $scope.task_id = $routeParams.id;
        $scope.task = {};
        $scope.page = {
            btnActive: true,
            btnText: "我要投标"
        };

        $scope.task_images = [];

        $scope.init = function(){
            $ajax.get({
                url: "/task/getBidTask",
                data: {
                    taskId: $scope.task_id
                },
                success: function(d){
                    $scope.task = d.data;
                    $scope.task_images = $scope.task.imageFiles || [];
                    if($scope.task.hasBid){
                        $scope.page.btnActive = false;
                        $scope.page.btnText = "已投标";
                    }else if($scope.task.status > 1){
                        $scope.page.btnActive = false;
                        $scope.page.btnText = "已截止";
                    }else if($scope.task.memberId == $rootScope.user.id){
                        $scope.page.btnActive = false;
                    }
                },
                error: function(e){
                    console.log(e);
                }
            });
        };
        $scope.init();

        $scope.getTaskPrice = function(data){
            if(data != {}){
//                var feeRange = 0;
//                if(data.minFee !== data.maxFee){
//                    feeRange = data.minFee + '-' + data.maxFee;
//                }else if(data.minFee == data.maxFee && data.minFee == 0){
//                    feeRange = data.prepayFee;
//                }else{
//                    feeRange = data.minFee;
//                }
                return data.prepayFee;
            }else{
                return 0;
            }
        };

        $scope.getDeadlineText = function(msc){
            var result = "";
            var sub = parseInt(msc) - (new Date().getTime());
            if(sub <= 0){
                result = "已截止";
            }else if(sub < 86400000){
                if(sub < 3600000){
                    result = Math.round(sub/60000) + "分钟";
                }else{
                    result = Math.floor(sub/3600000) + "小时";
                }
            }else{
                result = Math.floor(sub/86400000) + "天";
            }
            return result;
        };

        $scope.formatDate = function(msc){
            var date = new Date(parseInt(msc));
            return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
        };

        $scope.checkJoiner = function(){
            $navigate.go('/taskJoiner/' + $scope.task_id);
        }

        $scope.bidTask = function(){
            $navigate.go('/taskBid/' + $scope.task_id);
        };
    });
});