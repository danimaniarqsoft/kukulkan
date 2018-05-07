(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('TrabajoDetailController', TrabajoDetailController);

    TrabajoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Trabajo', 'Empleado', 'Tarea'];

    function TrabajoDetailController($scope, $rootScope, $stateParams, previousState, entity, Trabajo, Empleado, Tarea) {
        var vm = this;

        vm.trabajo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kukulkanApp:trabajoUpdate', function(event, result) {
            vm.trabajo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
