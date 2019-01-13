import * as React from 'react';

import Loader from 'react-loader-spinner';
import './Loader.scss';

const LoaderComponent: React.SFC = () => {
    return <>
        <div className='loader__background'></div>
        <div className='loader__icon'><Loader
            type='Triangle'
            color='#4169E1'
            height='200'
            width='200'
        /></div>
    </>;
};

export default LoaderComponent;