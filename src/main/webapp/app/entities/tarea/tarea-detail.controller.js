(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('TareaDetailController', TareaDetailController);

    TareaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tarea', 'Trabajo'];

    function TareaDetailController($scope, $rootScope, $stateParams, previousState, entity, Tarea, Trabajo) {
        var vm = this;

        vm.tarea = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kukulkanApp:tareaUpdate', function(event, result) {
            vm.tarea = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
