(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('TareaDialogController', TareaDialogController);

    TareaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tarea', 'Empleado'];

    function TareaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tarea, Empleado) {
        var vm = this;

        vm.tarea = entity;
        vm.clear = clear;
        vm.save = save;
        vm.empleados = Empleado.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tarea.id !== null) {
                Tarea.update(vm.tarea, onSaveSuccess, onSaveError);
            } else {
                Tarea.save(vm.tarea, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kukulkanApp:tareaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
