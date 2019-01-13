import * as React from 'react';
import { Alert } from 'react-bootstrap';
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
        return error &&
            <Alert bsStyle='danger'>
                <h4>You got an error!</h4>
                <p>{error}</p>
            </Alert>;
    }
}

const mapStateToProps = (state: any): IMapStateToProps => {
    return {
        error: state.request.error
    };
};

export default connect<IMapStateToProps>(mapStateToProps)(ErrorNotification);