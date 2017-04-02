(function() {
    'use strict';

    angular
        .module('opleSioeApp')
        .controller('ImprocedenteDialogController', ImprocedenteDialogController);

    ImprocedenteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Improcedente', 'Evaluacion'];

    function ImprocedenteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Improcedente, Evaluacion) {
        var vm = this;

        vm.improcedente = entity;
        vm.clear = clear;
        vm.save = save;
        vm.evaluacions = Evaluacion.query({filter: 'improcedente-is-null'});
        $q.all([vm.improcedente.$promise, vm.evaluacions.$promise]).then(function() {
            if (!vm.improcedente.evaluacion || !vm.improcedente.evaluacion.id) {
                return $q.reject();
            }
            return Evaluacion.get({id : vm.improcedente.evaluacion.id}).$promise;
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
            if (vm.improcedente.id !== null) {
                Improcedente.update(vm.improcedente, onSaveSuccess, onSaveError);
            } else {
                Improcedente.save(vm.improcedente, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('opleSioeApp:improcedenteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
