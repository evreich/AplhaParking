import * as React from 'react';
import { ControlLabel, FormControl, FormGroup } from 'react-bootstrap';

export interface IFieldGroupProps {
    id: string;
    type: string;
    placeholder?: string;
    label: string;
    help?: string;
    value: any;
    onChange: (event: React.FormEvent<FormControl>) => void;
}

const FieldGroup: React.SFC<IFieldGroupProps> = ({ id, label, ...props }) => {
    return (
        <FormGroup controlId={id}>
            <ControlLabel>{label}</ControlLabel>
            <FormControl {...props} />
        </FormGroup>
    );
};

export default FieldGroup;