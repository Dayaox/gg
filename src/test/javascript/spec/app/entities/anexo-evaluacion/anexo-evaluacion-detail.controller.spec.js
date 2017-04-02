'use strict';

describe('Controller Tests', function() {

    describe('Anexo_evaluacion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAnexo_evaluacion, MockEvaluacion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAnexo_evaluacion = jasmine.createSpy('MockAnexo_evaluacion');
            MockEvaluacion = jasmine.createSpy('MockEvaluacion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Anexo_evaluacion': MockAnexo_evaluacion,
                'Evaluacion': MockEvaluacion
            };
            createController = function() {
                $injector.get('$controller')("Anexo_evaluacionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'opleSioeApp:anexo_evaluacionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
