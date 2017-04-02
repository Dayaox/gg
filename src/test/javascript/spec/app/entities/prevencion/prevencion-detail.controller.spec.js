'use strict';

describe('Controller Tests', function() {

    describe('Prevencion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPrevencion, MockAnexo_prevencion, MockPeticion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPrevencion = jasmine.createSpy('MockPrevencion');
            MockAnexo_prevencion = jasmine.createSpy('MockAnexo_prevencion');
            MockPeticion = jasmine.createSpy('MockPeticion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Prevencion': MockPrevencion,
                'Anexo_prevencion': MockAnexo_prevencion,
                'Peticion': MockPeticion
            };
            createController = function() {
                $injector.get('$controller')("PrevencionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'opleSioeApp:prevencionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
