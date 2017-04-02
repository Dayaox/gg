(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('ProcedenteDialogController', ProcedenteDialogController);

    ProcedenteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Procedente', 'Evaluacion'];

    function ProcedenteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Procedente, Evaluacion) {
        var vm = this;

        vm.procedente = entity;
        vm.clear = clear;
        vm.save = save;
        vm.evaluacions = Evaluacion.query({filter: 'procedente-is-null'});
        $q.all([vm.procedente.$promise, vm.evaluacions.$promise]).then(function() {
            if (!vm.procedente.evaluacion || !vm.procedente.evaluacion.id) {
                return $q.reject();
            }
            return Evaluacion.get({id : vm.procedente.evaluacion.id}).$promise;
        }).then(function(evaluacion) {
            vm.evaluacions.push(evaluacion);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.procedente.id !== null) {
                Procedente.update(vm.procedente, onSaveSuccess, onSaveError);
            } else {
                Procedente.save(vm.procedente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opleSioeApp:procedenteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
