(function() {
    'use strict';

    angular
        .module('kukulkanApp')
        .controller('PersonaDialogController', PersonaDialogController);

    PersonaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Persona'];

    function PersonaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Persona) {
        var vm = this;

        vm.persona = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.persona.id !== null) {
                Persona.update(vm.persona, onSaveSuccess, onSaveError);
            } else {
                Persona.save(vm.persona, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kukulkanApp:personaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.fechaLocalDate = false;
        vm.datePickerOpenStatus.fechaZoneDateTime = false;

        vm.setImagen = function ($file, persona) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        persona.imagen = base64Data;
                        persona.imagenContentType = $file.type;
                    });
                });
            }
        };

        vm.setImagenAnyBlob = function ($file, persona) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        persona.imagenAnyBlob = base64Data;
                        persona.imagenAnyBlobContentType = $file.type;
                    });
                });
            }
        };

        vm.setImagenBlob = function ($file, persona) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        persona.imagenBlob = base64Data;
                        persona.imagenBlobContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.instante = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
