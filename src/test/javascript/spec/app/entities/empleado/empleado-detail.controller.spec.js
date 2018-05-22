'use strict';

describe('Controller Tests', function() {

    describe('Empleado Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEmpleado, MockDireccion, MockTrabajo, MockDepartamento, MockTarea;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEmpleado = jasmine.createSpy('MockEmpleado');
            MockDireccion = jasmine.createSpy('MockDireccion');
            MockTrabajo = jasmine.createSpy('MockTrabajo');
            MockDepartamento = jasmine.createSpy('MockDepartamento');
            MockTarea = jasmine.createSpy('MockTarea');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Empleado': MockEmpleado,
                'Direccion': MockDireccion,
                'Trabajo': MockTrabajo,
                'Departamento': MockDepartamento,
                'Tarea': MockTarea
            };
            createController = function() {
                $injector.get('$controller')("EmpleadoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kukulkanApp:empleadoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
