define(['angular'], function(angular){
    angular.module('task-joiner-controller', []).controller('taskJoinerController', function($scope, $routeParams, $navigate, $ajax){
        $scope.task_id = $routeParams.id;
//        $scope.data = [{"id":8,"bid":6,"content":"adadadadadadadad","nickName":"test","bidNumber":"TB261021896266639567","avatar":"./images/wx_icon_hy01@3x.png",
//            "price":200,"time":"1474791258767","bindBid":1,"arriveTime":4,"verify":true},
//            {"id":8,"bid":4,"content":"测试测试测试测试测试测试测试测试","nickName":"testtest","bidNumber":"TB261033291482738461","avatar":"./images/wx_icon_hy01@3x.png",
//                "price":100,"time":"1474119619665","bindBid":0,"arriveTime":1,"verify":false}]

        $scope.init = function(){
            $ajax.get({
                url: "/task/getTaskMember",
                data: {
                    taskId: $scope.task_id
                },
                success: function(d){
                    $scope.data = d.data;
                },
                error: function(e){
                    console.log(e);
                }
            });
        };
        $scope.init();

        $scope.formatDate = function(msc){
            var date = new Date(parseInt(msc));
            return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
        };
    });
});