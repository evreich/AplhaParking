import * as React from 'react';
import * as ReactDOM from 'react-dom';
import Hello from './components/common/MainLayout';

declare let module: any;

const rootElem = document.getElementById('react-root');
ReactDOM.render(<Hello />, rootElem);

if (module.hot)
    module.hot.accept('./components/common/MainLayout', () => {
        const NextApp = require('./components/common/MainLayout').default;
        ReactDOM.render(<NextApp />, rootElem);
    });