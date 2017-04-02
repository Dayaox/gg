'use strict';

describe('Controller Tests', function() {

    describe('Anexo_prevencion Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAnexo_prevencion, MockPrevencion;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAnexo_prevencion = jasmine.createSpy('MockAnexo_prevencion');
            MockPrevencion = jasmine.createSpy('MockPrevencion');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Anexo_prevencion': MockAnexo_prevencion,
                'Prevencion': MockPrevencion
            };
            createController = function() {
                $injector.get('$controller')("Anexo_prevencionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'opleSioeApp:anexo_prevencionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
