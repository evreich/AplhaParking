import * as React from 'react';
import { connect } from 'react-redux';

interface IMapStateToProps {
    error: string;
}

class ErrorNotification extends React.Component<IMapStateToProps> {
    constructor(props: any) {
        super(props);
    }

    render() {
        const { error } = this.props;
        return error && <div style={{color: 'red'}}>{error}</div>;
    }
}

const mapStateToProps = (state: any): IMapStateToProps => {
    return {
        error: state.request.error
    };
};

export default connect<IMapStateToProps>(mapStateToProps)(ErrorNotification);