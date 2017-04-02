'use strict';

describe('Controller Tests', function() {

    describe('Peticion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPeticion, MockUsuario, MockPeticionario, MockAnexo, MockPrevencion, MockEvaluacion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPeticion = jasmine.createSpy('MockPeticion');
            MockUsuario = jasmine.createSpy('MockUsuario');
            MockPeticionario = jasmine.createSpy('MockPeticionario');
            MockAnexo = jasmine.createSpy('MockAnexo');
            MockPrevencion = jasmine.createSpy('MockPrevencion');
            MockEvaluacion = jasmine.createSpy('MockEvaluacion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Peticion': MockPeticion,
                'Usuario': MockUsuario,
                'Peticionario': MockPeticionario,
                'Anexo': MockAnexo,
                'Prevencion': MockPrevencion,
                'Evaluacion': MockEvaluacion
            };
            createController = function() {
                $injector.get('$controller')("PeticionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'opleSioeApp:peticionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
