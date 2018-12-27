import * as React from 'react';
import * as ReactDOM from 'react-dom';
import Router from './components/routes/Router';

import './stylesheets/common.scss';

declare let module: any;

const rootElem = document.getElementById('react-root');
ReactDOM.render(<Router />, rootElem);

if (module.hot)
    module.hot.accept('./components/routes/Router', () => {
        const NextApp = require('./components/routes/Router').default;
        ReactDOM.render(<NextApp />, rootElem);
    });