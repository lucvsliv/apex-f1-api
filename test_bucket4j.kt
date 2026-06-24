import io.github.bucket4j.distributed.ExpirationAfterWriteStrategy
import java.time.Duration

fun main() {
    val strategy = ExpirationAfterWriteStrategy.basedOnTimeForRefillingBucketUpToMax(Duration.ofSeconds(10))
    println(strategy)
}
