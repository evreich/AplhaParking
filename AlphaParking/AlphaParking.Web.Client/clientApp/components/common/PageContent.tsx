import * as React from 'react';
import { connect } from 'react-redux';

import Loader from './Loader';
import './PageContent.scss';

interface IMapStateToProps {
    isFetching: boolean;
}

const PageContent: React.SFC<IMapStateToProps> = ({ isFetching, children }) => {
    return <div className='container'>
        {isFetching ? <Loader /> : {children}}
    </div>;
};

const mapStateToProps = (state: any): IMapStateToProps => {
    const { request } = state;
    return {
        isFetching: request.isFetching
    };
};

export default connect<IMapStateToProps>(mapStateToProps)(PageContent);