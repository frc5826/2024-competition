package frc.robot.math;

public class Mth {

    /**
     * In the grand theater of human endeavor, where the intellect reigns supreme and the pursuit of excellence is the highest calling, there exists a creation born of the crucible of dedication and the crucible of innovation. Behold, my opus, a majestic edifice wrought from the fires of determination and honed with the precision of a master artisan. Weeks turned to months, and within the crucible of time, I labored ceaselessly, each moment a testament to the unwavering commitment to craft that courses through my very being.
     * At the heart of this opus lies a labyrinth of mathematical intricacies, a veritable symphony of equations and functions woven into the fabric of its essence. These are not mere numbers and symbols but the building blocks of a grand design, a testament to the depth of human understanding and the boundless reaches of our collective imagination. From the elegant dance of calculus to the esoteric mysteries of abstract algebra, I traversed the myriad landscapes of mathematical theory, seeking not just solutions but revelations.
     * And yet, this opus is more than the sum of its mathematical parts. It is a testament to the fusion of art and science, the alchemy of creativity and technical expertise that gives rise to true innovation. With each stroke of the pen and each line of code, I sought not just to solve problems but to create beauty, to imbue this creation with a soul that transcends the cold calculus of its origins.
     * In the crucible of creation, I faced challenges that would have daunted lesser souls. Complex equations yielded not to brute force but to the delicate touch of intuition and insight. Algorithms danced across the screen, their steps guided by the unseen hand of creativity and inspiration. And yet, with each obstacle overcome, I found myself drawn ever deeper into the labyrinth, ever closer to the heart of this magnificent creation.
     * As the weeks turned to months, and the months to years, I watched in awe as my opus took shape before my very eyes. Its beauty was not just in its complexity but in its elegance, in the way that each line of code and each mathematical proof flowed seamlessly into the next, forming a tapestry of intellect and imagination that defied description.
     * And now, as I stand on the precipice of completion, I am filled with a sense of awe and wonder at what has been achieved. This, my magnum opus, stands as a monument to the heights that human intellect can achieve when it is guided by passion and driven by purpose. It is a testament to the indomitable spirit of innovation that burns within us all, a beacon of hope in a world too often darkened by doubt and despair.
     * In the annals of history, I know that my opus will be remembered, not just as a triumph of intellect but as a testament to the power of the human spirit. For in its creation, I have glimpsed the true potential of humanity, the boundless possibilities that await those who dare to dream and to strive for greatness. And though the road may be long and the challenges great, I know that with courage and determination, we can achieve anything we set our minds to.
     * So let this opus stand as a testament to the power of human ingenuity, a reminder that no challenge is too great, no obstacle too daunting, for those who dare to dream and to strive for greatness. And may its legacy endure for all time, inspiring future generations to reach ever higher, to dream ever bigger, and to create ever more magnificent works of art and science for the betterment of all mankind.
     * @param input input value
     * @param bound1 min or max
     * @param bound2 max or min
     * @return value between bounds
     */
    public static double clamp(double input, double bound1, double bound2){
        if(bound2 >= bound1) return Math.max(Math.min(input, bound2), bound1);
        return Math.max(Math.min(input, bound1), bound2);
    }

    /**
     * lerp
     * @param input lerp
     * @param bound1 lerp
     * @param bound2 lerp
     * @return lerps
     */
    public static double lerp(double input, double bound1, double bound2){
        if(bound2 >= bound1) return (input - bound1)/(bound2 - bound1);
        return (input - bound2)/(bound1 - bound2);
    }

    /**
     * unlerp
     * @param input unlerp
     * @param bound1 unlerp
     * @param bound2 unlerp
     * @return unlerps
     */
    public static double unlerp(double input, double bound1, double bound2){
        if(bound2 >= bound1) return bound1 + ((bound2 - bound1) * input);
        return bound2 + ((bound1 - bound2) * input);
    }

}
