(function() {
    'use strict';
    angular
        .module('opleSioeApp')
        .factory('Anexo_evaluacion', Anexo_evaluacion);

    Anexo_evaluacion.$inject = ['$resource'];

    function Anexo_evaluacion ($resource) {
        var resourceUrl =  'api/anexo-evaluacions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
