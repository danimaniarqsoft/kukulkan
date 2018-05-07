'use strict';

describe('Controller Tests', function() {

    describe('Trabajo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTrabajo, MockEmpleado, MockTarea;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTrabajo = jasmine.createSpy('MockTrabajo');
            MockEmpleado = jasmine.createSpy('MockEmpleado');
            MockTarea = jasmine.createSpy('MockTarea');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Trabajo': MockTrabajo,
                'Empleado': MockEmpleado,
                'Tarea': MockTarea
            };
            createController = function() {
                $injector.get('$controller')("TrabajoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'kukulkanApp:trabajoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
