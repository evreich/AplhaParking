export function setItem(key: string, value: any) {
    if (!key) throw new Error('Key is not defined!');
    if (!value) throw new Error('Value is not defined!');

    const jsonValue = JSON.stringify(value);
    localStorage.setItem(key, jsonValue);
}

export function getItem(key: string) {
    if (!key) throw new Error('Key is not defined!');

    const jsonValue = localStorage.getItem(key);
    if (jsonValue)
        return JSON.parse(jsonValue);
    else
        return null;
}