var format = function () {
    let reg = RegExp("\\{\\}");
    for (var a = arguments[0], b = 1; b < arguments.length; b++) {
        a = a.replace(reg, arguments[b]);
    }
    return a
};
export default { format };