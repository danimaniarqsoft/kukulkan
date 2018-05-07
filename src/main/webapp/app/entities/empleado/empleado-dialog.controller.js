(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('EmpleadoDialogController', EmpleadoDialogController);

    EmpleadoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Empleado', 'Departamento', 'Direccion', 'Trabajo'];

    function EmpleadoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Empleado, Departamento, Direccion, Trabajo) {
        var vm = this;

        vm.empleado = entity;
        vm.clear = clear;
        vm.save = save;
        vm.departamentos = Departamento.query();
        vm.direccions = Direccion.query({filter: 'empleado-is-null'});
        $q.all([vm.empleado.$promise, vm.direccions.$promise]).then(function() {
            if (!vm.empleado.direccion || !vm.empleado.direccion.id) {
                return $q.reject();
            }
            return Direccion.get({id : vm.empleado.direccion.id}).$promise;
        }).then(function(direccion) {
            vm.direccions.push(direccion);
        });
        vm.trabajos = Trabajo.query();
        vm.empleados = Empleado.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.empleado.id !== null) {
                Empleado.update(vm.empleado, onSaveSuccess, onSaveError);
            } else {
                Empleado.save(vm.empleado, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kukulkanApp:empleadoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
