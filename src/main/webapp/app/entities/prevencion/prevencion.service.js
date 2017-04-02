(function() {
    'use strict';
    angular
        .module('opleSioeApp')
        .factory('Prevencion', Prevencion);

    Prevencion.$inject = ['$resource'];

    function Prevencion ($resource) {
        var resourceUrl =  'api/prevencions/:id';

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
