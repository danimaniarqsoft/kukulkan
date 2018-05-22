(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('EmpleadoDetailController', EmpleadoDetailController);

    EmpleadoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Empleado', 'Direccion', 'Trabajo', 'Departamento', 'Tarea'];

    function EmpleadoDetailController($scope, $rootScope, $stateParams, previousState, entity, Empleado, Direccion, Trabajo, Departamento, Tarea) {
        var vm = this;

        vm.empleado = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kukulkanApp:empleadoUpdate', function(event, result) {
            vm.empleado = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
