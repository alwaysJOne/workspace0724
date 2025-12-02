//경로 상수
export const ROUTES = {
    HOME: "/",
    TODOS: '/todos',

    CATEGORY: (category) => `/category/${category}`,
}

export const CATEGORYS = {
    WORK: 'work',
    STUDY: 'study',
    HEALTH: 'health',
}

export const CATEGORY_NAMES = {
    [CATEGORYS.WORK]: '업무',
    [CATEGORYS.STUDY]: '학습',
    [CATEGORYS.HEALTH]: '건강',
}