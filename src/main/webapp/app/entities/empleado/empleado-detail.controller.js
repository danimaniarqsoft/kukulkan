(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('EmpleadoDetailController', EmpleadoDetailController);

    EmpleadoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Empleado', 'Departamento', 'Direccion', 'Trabajo'];

    function EmpleadoDetailController($scope, $rootScope, $stateParams, previousState, entity, Empleado, Departamento, Direccion, Trabajo) {
        var vm = this;

        vm.empleado = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kukulkanApp:empleadoUpdate', function(event, result) {
            vm.empleado = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
