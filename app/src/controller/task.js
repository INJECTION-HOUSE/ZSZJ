define(['angular', 'menu', 'mobiscroll', 'dragRefresh'], function(angular){
    angular.module('task-controller', ['menu','drag-refresh']).controller('taskController', function($scope, $rootScope, $navigate, $timeout, $ajax){
        $scope.taskList = [];
        var type = $navigate.param("type") || 0;
        $scope.search = {
            startDate: null,
            endDate: null,
            status: 0,
            type: type
        };

        $scope.init = function(){
            $scope.search.startDate = $(".start_time").val();
            $scope.search.endDate = $(".end_time").val();
            $scope.search.status = $(".filter_status").val();

            if($scope.search.startDate && $scope.search.endDate && new Date($scope.search.startDate) > new Date($scope.search.endDate)){
                alert("结束时间应大于起始时间");
                return false;
            }

            if($scope.search.startDate){
                var sd = new Date($scope.search.startDate).setHours(0, 0, 0, 0);
                $scope.search.startDate = new Date(sd).getTime();
            }
            if($scope.search.endDate){
                var ed = new Date($scope.search.endDate).setHours(0, 0, 0, 0);
                $scope.search.endDate = new Date(ed).getTime();
            }
            $ajax.get({
                url: "/task/getTaskList",
                data: $scope.search,
                success:  function(d){
                    $scope.taskList = d.data;
                },
                error: function(e){
                    console.log(e);
                }
            });
        };

        $scope.init();
        $scope.getTypeText = function(type){
            return "维修类型";
        };

//        var obj = navigator.geolocation.getCurrentPosition(showPosition);
        //console.log(obj);
//        function showPosition(position){
//            console.log(position);
//            var lng = position.coords.longitude;
//            var lat = position.coords.latitude;
//        }

        $(".filter_status").mobiscroll().select({
            lang: "zh",
            theme: "mobiscroll",
            display: "bottom",
            placeholder: "请点击选择",
            onClose: function(){
                $timeout(function(){
                    $(".task_filter_dialog").mobiscroll('show');
                });
            }
        });

        $(".start_time, .end_time").mobiscroll().date({
            lang: "zh",
            theme: "mobiscroll",
            display: "bottom",
            onClose: function(){
                $timeout(function(){
                    $(".task_filter_dialog").mobiscroll('show');
                });
            }
        });

        $(".filter_icon").bind("click", function(){
            $timeout(function(){
                $(".task_filter_dialog").mobiscroll('show');
            });
        });

        $(".task_filter_dialog").mobiscroll().widget({
            theme: "mobiscroll",
            display: "bottom",
            animate: "pop",
            closeOnOverlayTap: false,
            cssClass: 'white_content',
            buttons: [{
                text: "确定",
                handler: function (event, inst) {
                    console.log("ok clicked");
                    inst.hide();
                    $(".filter_icon").show();
                    $scope.init();
                }
            },{
                text: "取消",
                handler: "cancel"
            }],
            onShow: function(){
                $timeout(function(){
                    $(".filter_icon").hide();
                });
            },
            onClose: function(){
                $timeout(function(){
                    $(".filter_icon").show();
                });
            }
        });

        $scope.getStatusText = function(status){
            var result = '';
            switch(status){
                case 0:
                    result = '投标中';
                    break;
                case 1:
                    result = '选标中';
                    break;
                case 2:
                    result = '维修中';
                    break;
                case 3:
                    result = '已完工';
                    break;
                case 4:
                    result = '已付款';
                    break;
                case 5:
                    result = '已结束';
                    break;
                case 6:
                    result = '纠纷';
                    break;
                default:
                    result = '投标中';
                    break;
            }

            return result;
        };

        $scope.getSimplePosition = function(pos){
            return pos ? pos.substr(0, 4) : "";
        };

        $scope.getDeadlineText = function(msc){
            var result = "";
            var sub = parseInt(msc) - (new Date().getTime());
            if(sub <= 0){
                result = "已截止";
            }else if(sub < 86400000){
                if(sub < 3600000){
                    result = Math.round(sub/60000) + "分钟后";
                }else{
                    result = Math.floor(sub/3600000) + "小时后";
                }
            }else{
                result = Math.floor(sub/86400000) + "天后";
            }
            return result;
        };

        $scope.getJoinerCount = function(joiner){
            return joiner ? joiner.split(",").length : 0;
        };

        $scope.taskDetail = function(id){
            $navigate.go('/taskDetail/' + id);
        }

        $scope.hasBid = function(joiner){
            return joiner ? joiner.indexOf($scope.user.id) != -1 : false;
        }
    });
});