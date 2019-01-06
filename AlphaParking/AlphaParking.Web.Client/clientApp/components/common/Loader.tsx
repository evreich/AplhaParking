import * as React from 'react';

import Loader from 'react-loader-spinner';

const LoaderComponent: React.SFC = () => {
    return <Loader
        type='Triangle'
        color='#4169E1'
        height='100'
        width='100'
    />;
};

export default LoaderComponent;