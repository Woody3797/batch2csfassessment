export interface Bundle {
    bundleId: string
    date: string
    title: string
    name: string
    comments: string
    urls: URL[]
}

export interface URL {
    url: string
}