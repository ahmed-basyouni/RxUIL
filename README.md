# RxUIL
A reactive layer on top of universal image loader (easy and hassle-free) using [RxJava 2](https://github.com/ReactiveX/RxJava)

# how to use
#### easy just call

````
RxUILObservableBuilder.builder()
                .imageLoader(imageLoader)
                .displayOptions(displayImageOptions)
                .preview(imageView)
                .url(url)
                .subscribe(new UILConsumer() {
                    @Override
                    public void onNext(RxUILObservableBuilder.RXUILObject value) {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        // you can here use d.dispose(); to cancel the prview task 
                    }
                });
````     

### RXUILObject Contain

        String imageUri;
        View view;
        Bitmap loadedImage;

#### That's it happy coding

# License

  The MIT License (MIT)

    Copyright (c) 2017 Ahmed basyouni

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
