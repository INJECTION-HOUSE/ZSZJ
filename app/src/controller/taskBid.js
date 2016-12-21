define(['angular'], function(angular){
    angular.module('task-bid-controller', []).controller('taskBidController', function($scope, $routeParams, $navigate, $ajax){
        $scope.data = {
            taskId: $routeParams.id,
            bidContent: "",
            price: null,
            arriveTime: 0
        }

        $scope.formatDate = function(msc){
            var date = new Date(parseInt(msc));
            return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
        };

        $(".radio").bind("click", function(e){
            $(".radio").removeClass("active");
            $(e.currentTarget).addClass("active");
            var arriveTime = $(e.currentTarget).attr("value");
            $scope.data.arriveTime = arriveTime;
        });

        $scope.doCancel = function(){
            $navigate.back();
        };
        $scope.doCommit = function(){
            if(!$scope.data.bidContent || $scope.data.bidContent.trim() == ""){
                return false;
            }
            var reg = new RegExp('^[0-9]*[1-9][0-9]*$');
            if(!$scope.data.price || !reg.test($scope.data.price)){
                return false;
            }
            if(!$scope.data.arriveTime){
                return false;
            }

            $ajax.post({
                url: "/task/saveTaskBid",
                data: $scope.data,
                success: function(d){
                    console.log(d);
                    $navigate.go('/taskDetail/' + $scope.data.taskId);
                },
                error: function(e){
                    console.log(e);
                }
            });
        };
    });
});