'use strict';

describe('Controller Tests', function() {

    describe('Evaluacion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEvaluacion, MockAnexo_evaluacion, MockProcedente, MockImprocedente, MockPresentacion, MockPeticion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEvaluacion = jasmine.createSpy('MockEvaluacion');
            MockAnexo_evaluacion = jasmine.createSpy('MockAnexo_evaluacion');
            MockProcedente = jasmine.createSpy('MockProcedente');
            MockImprocedente = jasmine.createSpy('MockImprocedente');
            MockPresentacion = jasmine.createSpy('MockPresentacion');
            MockPeticion = jasmine.createSpy('MockPeticion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Evaluacion': MockEvaluacion,
                'Anexo_evaluacion': MockAnexo_evaluacion,
                'Procedente': MockProcedente,
                'Improcedente': MockImprocedente,
                'Presentacion': MockPresentacion,
                'Peticion': MockPeticion
            };
            createController = function() {
                $injector.get('$controller')("EvaluacionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'opleSioeApp:evaluacionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
